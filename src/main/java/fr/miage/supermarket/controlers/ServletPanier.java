package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.dao.PromotionDAO;
import fr.miage.supermarket.dao.StockDAO;
import fr.miage.supermarket.dao.UtilisateurDAO;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.models.Panier;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ProduitPanier;
import fr.miage.supermarket.models.StatutCommande;
import fr.miage.supermarket.models.Utilisateur;

/**
 * Servlet de gestion du panier (affichage/validation)
 */
public class ServletPanier extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ProduitDAO produitDAO;
	private PromotionDAO promotionDAO;
	private CommandeDAO commandeDAO;
	private UtilisateurDAO utilisateurDAO;
	private MagasinDAO magasinDAO;
	private StockDAO stockDAO;

	private Map<String, Float> promotions;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPanier() {
		super();
		this.produitDAO = new ProduitDAO();
		this.promotionDAO = new PromotionDAO();
		this.commandeDAO = new CommandeDAO();
		this.stockDAO = new StockDAO();
		this.promotions = new HashMap<>();
		this.utilisateurDAO = new UtilisateurDAO();
	}

	/**
	 * Méthode GET renvoyant vers la JSP d'affichage du panier.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Panier panier = getOrCreatePanier(session);
		updatePanierWithPromotions(panier);
		float totalPrix = panier.calculerPrixTotal();
		List<Magasin> magasins = magasinDAO.getAllMagasins();
		request.setAttribute("produits", panier.getPanier());
		request.setAttribute("totalPrix", totalPrix);
		request.setAttribute("magasins", magasins);
		request.setAttribute("promotions", promotions);

		String action = request.getParameter("action");
		// on vide le panier de la session 
		if("vider".equals(action)) {
		    session.removeAttribute("panier");
		    Utilisateur utilisateurActuel = (Utilisateur) session.getAttribute("utilisateur");
		    // on retire de la bd la commande non validée 
		    if(utilisateurActuel != null) {
				Commande commandeProvisoire = commandeDAO.getCommandeNonFinalisee(utilisateurActuel.getId());
				commandeDAO.supprimerCommande(commandeProvisoire);
		    }
		}
		request.getRequestDispatcher("/jsp/afficherPanier.jsp").forward(request, response);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Méthode POST permettant de gérer la validation du panier contenu en session.
	 * 
	 * @see {@link HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)};
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		validerPanier(request, response);
	}

	/**
	 * Récupère le panier contenu en session s'il existe, sinon associe un nouveau
	 * panier et le retourne.
	 * 
	 * @param session la session sur laquelle prendre le panier
	 * @return le panier
	 */
	private Panier getOrCreatePanier(HttpSession session) {
		Panier panier = (Panier) session.getAttribute("panier");
		if (panier == null) {
			panier = new Panier();
			session.setAttribute("panier", panier);
		}
		return panier;
	}

	/**
	 * Mets à jour le panier pour chaque produit avec la promotion associée si
	 * trouvée
	 * 
	 * @param panier le panier à mettre à jour
	 * @author EricB
	 */
	private void updatePanierWithPromotions(Panier panier) {
		List<String> eans = panier.getPanier().values().stream().map(ProduitPanier::getEan)
				.collect(Collectors.toList());
		promotions = promotionDAO.getPromotionsPourProduits(eans);
		panier.getPanier().values()
				.forEach(produit -> produit.setTauxPromotion(promotions.getOrDefault(produit.getEan(), null)));
	}

	/**
	 * Valide le panier afin de le transformer en commande.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void validerPanier(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Panier panier = (Panier) session.getAttribute("panier");
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

		if (panier == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Vous ne pouvez pas valider la commande, votre panier est vide.");
			return;
		}

		if (utilisateur == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Vous devez vous connecter pour pouvoir valider votre commande.");
			return;
		}

		String magasinIdStr = request.getParameter("magasin");
		String dateStr = request.getParameter("date");
		String horaireStr = request.getParameter("horaire");

		if (magasinIdStr != null && dateStr != null && horaireStr != null) {
			try {
				int magasinId = Integer.parseInt(magasinIdStr);
				Magasin magasin = MagasinDAO.getMagasinById(magasinId);
				Commande commandeNonFinalisee = commandeDAO.getCommandeNonFinalisee(utilisateur.getId());
				if (commandeNonFinalisee != null) {
					List<Produit> produitsNonEnStock = stockDAO.getProduitsNonEnStockPourCommande(
							new SimpleDateFormat("yyyy-MM-dd").parse(dateStr), magasin, commandeNonFinalisee);
					if (!produitsNonEnStock.isEmpty()) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						StringBuilder xmlResponse = new StringBuilder();
						xmlResponse.append("<produits>");
						for (Produit produit : produitsNonEnStock) {
							xmlResponse.append("<produit id='").append(produit.getEan()).append("'>");
							xmlResponse.append("<libelle>").append(produit.getLibelle()).append("</libelle>");
							xmlResponse.append("<poids>").append(produit.getPoids()).append("</poids>");
							xmlResponse.append("<prix>").append(produit.getPrix()).append("</prix>");
							xmlResponse.append("<conditionnement>").append(produit.getConditionnement())
									.append("</conditionnement>");
							if (produit.getRepertoireImage() != null) {
								xmlResponse.append("<image>").append(produit.getRepertoireImage()).append("</image>");
							}
							/**
							 * if (promotion != null) {
							 * xmlResponse.append("<tauxPromotion>").append(promotion).append("</tauxPromotion>");
							 * }
							 **/
							xmlResponse.append("<produitsRemplacement>");
							List<Produit> produitsRemplacement = produitDAO.getProduitsRemplacementEnStock(produit,
									magasin, new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
							for (Produit produitRemplacement : produitsRemplacement) {
								xmlResponse.append("<produitRemplacement id='").append(produitRemplacement.getEan())
										.append("'>");
								xmlResponse.append("<libelle>").append(produitRemplacement.getLibelle())
										.append("</libelle>");
								xmlResponse.append("<poids>").append(produitRemplacement.getPoids()).append("</poids>");
								xmlResponse.append("<prix>").append(produitRemplacement.getPrix()).append("</prix>");
								xmlResponse.append("<conditionnement>").append(produitRemplacement.getConditionnement())
										.append("</conditionnement>");
								if (produit.getRepertoireImage() != null) {
									xmlResponse.append("<image>").append(produitRemplacement.getRepertoireImage())
											.append("</image>");
								}
								xmlResponse.append("</produitRemplacement>");
							}
							xmlResponse.append("</produitsRemplacement>");

							xmlResponse.append("</produit>");

						}
						xmlResponse.append("</produits>");

						response.setContentType("application/xml");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(xmlResponse.toString());
						return;
					}
				}

				Commande commande = creerEtEnregistrerCommande(panier, magasinId, dateStr, horaireStr, utilisateur);
				majPointsUtilisateur(request.getParameter("pointsUtilises"), commande.getUtilisateur(), panier,
						session);
				// Commande enregistrée : on vide le panier
				session.removeAttribute("panier");

				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("Commande validée");
			} catch (NumberFormatException | ParseException e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Erreur survenue lors de l'enregistrement de la commande");
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Erreur survenue lors de l'enregistrement de la commande");
		}
	}

	/**
	 * Créée et enregistre une nouvelle commande à partir du panier
	 * 
	 * @param panier      le panier depuis lequel créer une commande
	 * @param magasinId   l'id du magasin de commande
	 * @param dateStr     la date de reception de la commande
	 * @param horaireStr  l'horaire de reception de la commande
	 * @param utilisateur l'utilisateur passant commande
	 * @return la commande nouvellement créée
	 * @throws ParseException
	 */
	private Commande creerEtEnregistrerCommande(Panier panier, int magasinId, String dateStr, String horaireStr,
			Utilisateur utilisateur) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate currentDate = LocalDate.now();

		Commande commande = commandeDAO.getCommandeNonFinalisee(utilisateur.getId());
		commande.setStatut(StatutCommande.EN_COURS);
		commande.setIdMagasin(magasinId);
		commande.setDateCommande(currentDate);
		commande.setDateRetrait(localDate);
		commande.setHoraireRetrait(horaireStr);
		commande.finaliserCommande(panier.calculerPrixTotal());

		utilisateurDAO.mettreAJourUtilisateur(commande.getUtilisateur());

		
		//Pour tous les produits du panier, on décrémente le stock du magasin
		StockDAO stockDAO = new StockDAO();
		for (Entry<String, ProduitPanier> entry : panier.getPanier().entrySet()) {
			stockDAO.retirerProduitCommandesStock(entry.getValue().getEan(), magasinId, entry.getValue().getQuantite());
		}
		
		return commandeDAO.creerOuMajCommande(commande);
	}

	/**
	 * Gestion de la mise à jour des points de fidélité de l'utilisateur
	 * @param pointsUtilisesStr les points que l'utilisateur a utilisé pour la commande
	 * @param utilisateur l'utilisateur concerné
	 * @param panier le panier sur lequel les points ont été utilisés
	 */
	private void majPointsUtilisateur(String pointsUtilisesStr, Utilisateur utilisateur, Panier panier, HttpSession session) {
		int pointsUtilises = 0;

		if (pointsUtilisesStr != null && !pointsUtilisesStr.isEmpty()) {
			try {
				pointsUtilises = Integer.parseInt(pointsUtilisesStr);
			} catch (NumberFormatException e) {
				pointsUtilises = 0;
			}
		}

		double prixTotal = panier.calculerPrixTotal();
		int pointActuel = utilisateur.getPoints();
		if (utilisateur.getPoints() == null) {
			pointActuel = 0;
		}
		if (pointsUtilises < 0) {
			pointsUtilises = 0;
		} else if (pointsUtilises > pointActuel) {
			pointsUtilises = pointActuel;
		}

		int maxPointsUtilisables = (int) Math.floor(prixTotal * 10);
		
		if (pointsUtilises > maxPointsUtilisables) {
			pointsUtilises = maxPointsUtilisables;
		}

		//double reduction = pointsUtilises / 10.0; // 10 points = 1€ de réduction
		//double prixTotalAvecReduction = prixTotal - reduction;
		utilisateur.setPoints(utilisateur.getPoints() - pointsUtilises);
		Utilisateur utilisateurAJour = utilisateurDAO.mettreAJourUtilisateur(utilisateur);
        Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("utilisateur");
        utilisateurSession.setPoints(utilisateurAJour.getPoints());
	}
	
}
