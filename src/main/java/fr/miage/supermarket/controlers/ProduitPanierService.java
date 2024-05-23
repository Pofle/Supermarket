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
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.http.*;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.dao.PromotionDAO;
import fr.miage.supermarket.models.*;

/**
 * Servlet de gestion du panier.
 * 
 */
public class ProduitPanierService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ProduitDAO produitDAO;
	private PromotionDAO promotionDAO;
	private CommandeDAO commandeDAO;
	private MagasinDAO magasinDAO;

	private Map<String, Float> promotions;

	public ProduitPanierService() {
		this.produitDAO = new ProduitDAO();
		this.promotionDAO = new PromotionDAO();
		this.commandeDAO = new CommandeDAO();
		this.magasinDAO = new MagasinDAO();
		this.promotions = new HashMap<>();
	}

	/**
	 * Méthode GET permettant de récupérer sous forme de XML des informations sur
	 * les produits contenus dans le panier en session. Avec le paramètre
	 * displayOption renseigné à "all", renvoit l'ensemble des produits contenus en
	 * session, et le prix total du panier. Sans le paramètre displayOption
	 * renseigné à "all", renvoit le nombre de produits enregistrés dans le panier.
	 * 
	 * @see {@link HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)};
	 * @author EricB
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		Panier panier = (Panier) session.getAttribute("panier");
		// Si le panier n'existe pas en session, on le crée
		if (panier == null) {
			panier = new Panier();
			session.setAttribute("panier", panier);
		}
		String categorieIdString = request.getParameter("categorieId");
		if (categorieIdString != null) {
			Integer categorieId = Integer.parseInt(categorieIdString);
			List<Object[]> produitsWithPromoQuantite = produitDAO
					.findProduitsWithPromotionsAndPurchaseCountByCategorieId(categorieId);

			StringBuilder xmlResponse = new StringBuilder();
			xmlResponse.append("<produits>");
			for (Object[] produitPromoQuantite : produitsWithPromoQuantite) {
				Produit produit = (Produit) produitPromoQuantite[0];
				Float promotion = (Float) produitPromoQuantite[1];
				Long purchaseCount = (Long) produitPromoQuantite[2];

				xmlResponse.append("<produit id='").append(produit.getEan()).append("'>");
				xmlResponse.append("<libelle>").append(produit.getLibelle()).append("</libelle>");
				xmlResponse.append("<poids>").append(produit.getPoids()).append("</poids>");
				xmlResponse.append("<prix>").append(produit.getPrix()).append("</prix>");
				xmlResponse.append("<conditionnement>").append(produit.getConditionnement())
						.append("</conditionnement>");
				if (produit.getRepertoireImage() != null) {
					xmlResponse.append("<image>").append(produit.getRepertoireImage()).append("</image>");
				}
				if (promotion != null) {
					xmlResponse.append("<tauxPromotion>").append(promotion).append("</tauxPromotion>");
				}
				if (purchaseCount != null) {
					xmlResponse.append("<nombreAchats>").append(purchaseCount).append("</nombreAchats>");
				}

				xmlResponse.append("</produit>");
			}
			xmlResponse.append("</produits>");

			response.getWriter().write(xmlResponse.toString());
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter()
					.write("<response><nombreProduits>" + panier.getPanier().size() + "</nombreProduits></response>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Panier panier = getOrCreatePanier(session);
		String ean = request.getParameter("ean");
		String quantiteProduit = request.getParameter("quantite");
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

		ProduitPanier produitPanier = null;
		if (ean != null) {
			if (quantiteProduit != null) {
				int qttProduit = Integer.parseInt(quantiteProduit);
				if (qttProduit != 0) {
					produitPanier = panier.ajusterProduit(ean, qttProduit);
				}
			} else {
				produitPanier = ajouterProduitSession(panier, ean, response);
			}
			if (panier.getPanier().isEmpty()) {
				session.removeAttribute("panier");
			}

			if (utilisateur != null && produitPanier != null) {
				enregistrerPanierCommandeNonFinalisee(utilisateur, produitPanier);
			}

			response.getWriter()
					.write("<response><nombreProduits>" + panier.getPanier().size() + "</nombreProduits><prixTotal>"
							+ panier.calculerPrixTotal() + "</prixTotal>" + constructProduit(produitPanier)
							+ "</response>");
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private Panier getOrCreatePanier(HttpSession session) {
		Panier panier = (Panier) session.getAttribute("panier");
		if (panier == null) {
			panier = new Panier();
			session.setAttribute("panier", panier);
		}
		return panier;
	}

	private ProduitPanier ajouterProduitSession(Panier panier, String ean, HttpServletResponse response) {
		if (panier.produitExiste(ean)) {
			return panier.getPanier().get(ean);
		}
		Produit produit = produitDAO.getProduitByEan(ean);
		if (produit == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		Float promotion = promotionDAO.getPromotionPourProduit(produit.getEan());

		ProduitPanier produitPanier = new ProduitPanier(produit.getLibelle(), ean, 1, produit.getPrix(), promotion,
				produit.getConditionnement(), produit.getPoids(), produit.getRepertoireImage());
		panier.ajouterProduit(produitPanier);
		return produitPanier;
	}

	/**
	 * Construit une chaîne de caractères représentant la réponse en XML contenant
	 * l'ensemble des produits contenus en session dans le panier
	 * 
	 * @param panier le panier contenu en session
	 * @return la chaîne de caractères
	 */
	private String constructAllProduitsXML(Panier panier) {
		StringBuilder xmlResponse = new StringBuilder();
		xmlResponse.append("<panier>");
		xmlResponse.append("<produits>");

		List<String> eans = panier.getPanier().values().stream().map(ProduitPanier::getEan)
				.collect(Collectors.toList());
		Map<String, Float> promotions = promotionDAO.getPromotionsPourProduits(eans);
		for (ProduitPanier produitPanierItem : panier.getPanier().values()) {
			xmlResponse.append("<produit>");
			xmlResponse.append("<libelle>").append(produitPanierItem.getLibelle()).append("</libelle>");
			xmlResponse.append("<ean>").append(produitPanierItem.getEan()).append("</ean>");
			xmlResponse.append("<quantite>").append(produitPanierItem.getQuantite()).append("</quantite>");
			xmlResponse.append("<prix>").append(produitPanierItem.getPrix()).append("</prix>");
			if (promotions.get(produitPanierItem.getEan()) != null) {
				produitPanierItem.setTauxPromotion(promotions.get(produitPanierItem.getEan()));
				xmlResponse.append("<promotion>").append(promotions.get(produitPanierItem.getEan()))
						.append("</promotion>");
			}
			xmlResponse.append("<conditionnement>").append(produitPanierItem.getConditionnement())
					.append("</conditionnement>");
			if (produitPanierItem.getPoids() != null) {
				xmlResponse.append("<poids>").append(produitPanierItem.getPoids()).append("</poids>");
			}
			xmlResponse.append("<imageLocation>").append(produitPanierItem.getImage()).append("</imageLocation>");
			xmlResponse.append("</produit>");
		}
		xmlResponse.append("</produits>");
		xmlResponse.append("<prixTotal>").append(panier.calculerPrixTotal()).append("</prixTotal>");
		xmlResponse.append("</panier>");
		return xmlResponse.toString();
	}

	private String constructProduit(ProduitPanier produitPanier) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<produit>");
		strBuilder.append("<ean>").append(produitPanier.getEan()).append("</ean>");
		strBuilder.append("<libelle>").append(produitPanier.getLibelle()).append("</libelle>");
		strBuilder.append("<quantite>").append(produitPanier.getQuantite()).append("</quantite>");
		strBuilder.append("<prix>").append(produitPanier.getPrix()).append("</prix>");
		if (promotions.get(produitPanier.getEan()) != null) {
			produitPanier.setTauxPromotion(promotions.get(produitPanier.getEan()));
			strBuilder.append("<promotion>").append(promotions.get(produitPanier.getEan())).append("</promotion>");
		}
		strBuilder.append("<conditionnement>").append(produitPanier.getConditionnement()).append("</conditionnement>");
		if (produitPanier.getPoids() != null) {
			strBuilder.append("<poids>").append(produitPanier.getPoids()).append("</poids>");
		}
		if (produitPanier.getTauxPromotion() != null) {
			strBuilder.append("<promotion>").append(produitPanier.getTauxPromotion()).append("</promotion>");
		}
		strBuilder.append("<imageLocation>").append(produitPanier.getImage()).append("</imageLocation>");
		strBuilder.append("</produit>");
		return strBuilder.toString();
	}

	/**
	 * Enregistre les modifications d'un produit dans la commande non finalisée de
	 * l'utilisateur. Si aucune commande non finalisée n'existe pour l'utilisateur,
	 * crée une nouvelle commande non finalisée et y ajoute le produit. Si le
	 * produit existe déjà dans le panier non finalisé, met à jour sa quantité,
	 * sinon ajoute un nouveau lien entre la commande et le produit avec la quantité
	 * spécifiée. Si le panier devient vide après les modifications, supprime la
	 * commande non finalisée.
	 *
	 * @param utilisateur   L'utilisateur auquel appartient le panier non finalisé.
	 * @param produitPanier Le produit à enregistrer ou mettre à jour dans le panier
	 *                      non finalisé.
	 * @author EricB
	 */
	private void enregistrerPanierCommandeNonFinalisee(Utilisateur utilisateur, ProduitPanier produitPanier) {
		Commande commande = commandeDAO.getCommandeNonFinalisee(utilisateur.getId());

		Produit produit = produitDAO.getProduitByEan(produitPanier.getEan());

		if (commande == null) {
			commande = new Commande();
			commande.setUtilisateur(utilisateur);
			commande.setStatut(StatutCommande.NON_VALIDE);
			commande = commandeDAO.creerCommande(commande);
		}

		LinkCommandeProduitId id = new LinkCommandeProduitId(commande.getId_commande(), produit.getEan());
		LinkCommandeProduit link = commandeDAO.getLinkCommandeProduitById(id);

		if (link != null) {
			link.setQuantite(produitPanier.getQuantite());
			commandeDAO.updateLinkCommandeProduit(link);
		} else {
			link = new LinkCommandeProduit(commande, produit, produitPanier.getQuantite());
			commandeDAO.updateLinkCommandeProduit(link);
		}

		commande = commandeDAO.getCommandeNonFinalisee(utilisateur.getId());
		if (commande.getProduits().isEmpty()) {
			commandeDAO.supprimerCommande(commande);
		}
	}
}
