package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import fr.miage.supermarket.dao.UtilisateurDAO;
import fr.miage.supermarket.models.*;

/**
 * Servlet de gestion du panier.
 * 
 * @author EricB
 */
public class ProduitPanierService extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ProduitDAO produitDAO;
	private PromotionDAO promotionDAO;
	private CommandeDAO commandeDAO;

	public ProduitPanierService() {
		this.produitDAO = new ProduitDAO();
		this.promotionDAO = new PromotionDAO();
		this.commandeDAO = new CommandeDAO();
	}

	/**
	 * Méthode GET permettant de récupérer sous forme de XML des informations sur les produits contenus dans le panier en session.
	 * Avec le paramètre displayOption renseigné à "all", renvoit l'ensemble des produits contenus en session, et le prix total du panier.
	 * Sans le paramètre displayOption renseigné à "all", renvoit le nombre de produits enregistrés dans le panier.
	 * 
	 * @see {@link HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)};
	 * 
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
		
		String displayOption = request.getParameter("displayOption");
		// Si l'intégralité des produits sont demandés (displayOption = all)
		if (displayOption != null && displayOption.equals("all")) {
			response.getWriter().write(constructAllProduitsXML(panier));
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter()
				.write("<response><nombreProduits>" + panier.getPanier().size() + "</nombreProduits></response>");
	}

	/**
	 * Méthode POST permettant de gérer l'ajout d'un produit au panier contenu en session.
	 * Avec le paramètre quantite renseigné, ajuste la quantité du produit dont l'ean est celui passé en paramètres, en session.
	 * Sans le paramètre quantite renseigné, ajoute le produit dont l'ean est celui passé en paramètre, en session.
	 * 
	 * @see {@link HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)};
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
        if ("validerPanier".equals(action)) {
            validerPanier(request, response);
        } else {
			HttpSession session = request.getSession();
			Panier panier = (Panier) session.getAttribute("panier");
	
			String ean = request.getParameter("ean");
			String quantiteProduit = request.getParameter("quantite");
	
			if (ean != null) {
				// Si le panier n'existe pas en session, on le crée
				if (panier == null) {
					panier = new Panier();
					session.setAttribute("panier", panier);
				}
	
				if (quantiteProduit != null) {
					int qttProduit = Integer.parseInt(quantiteProduit);
					if (qttProduit != 0) {
						panier.ajusterProduit(ean, qttProduit);
					}
				} else {
					ajouterProduitSession(panier, ean, response);
				}
	
				if(panier.getPanier().isEmpty()) {
					session.removeAttribute("panier");
				}
				response.getWriter()
						.write("<response><nombreProduits>" + panier.getPanier().size() + "</nombreProduits></response>");
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
        }
	}

	/**
	 * Ajoute un produit dans le panier en session s'il n'existe pas déjà.
	 * 
	 * @param panier le panier contenu en session
	 * @param ean l'ean du produit à ajouter
	 * @param response la réponse donnée par le servlet
	 */
	private void ajouterProduitSession(Panier panier, String ean, HttpServletResponse response) {
		if (panier.produitExiste(ean)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		// On vérifie que le produit recherche existe
		Produit produit = produitDAO.getProduitByEan(ean);
		if (produit == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// Création de notre produitpanier, on ajoute le produit au panier
		ProduitPanier produitPanier = new ProduitPanier(produit.getLibelle(), ean, 1, produit.getPrix(), null,
				produit.getConditionnement(), produit.getPoids(), produit.getRepertoireImage());
		panier.ajouterProduit(produitPanier);
	}

	private void validerPanier(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
        Panier panier = (Panier) session.getAttribute("panier");
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (panier == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().write("Vous ne pouvez pas valider la commande, votre panier est vide.");
            return;
        }
        
        if(utilisateur == null) {
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
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

                // Convertir Date en LocalDate
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now(); // Date actuelle

                Commande commande = new Commande();
                commande.setUtilisateur(utilisateur);
                commande.setStatut(true);
                commande.setIdMagasin(magasinId);
                commande.setDateCommande(currentDate); // Date de commande
                commande.setDateRetrait(localDate);
                commande.setHoraireRetrait(horaireStr);
                commande = commandeDAO.creerCommande(commande);

                Set<LinkCommandeProduit> linkCommandeProduits = new HashSet<>();
                for (ProduitPanier produitPanier : panier.getPanier().values()) {
                    Produit produit = produitDAO.getProduitByEan(produitPanier.getEan());
                    LinkCommandeProduit link = new LinkCommandeProduit(commande, produit, produitPanier.getQuantite());
                    linkCommandeProduits.add(link);
                }
                commande.finaliserCommande(panier.calculerPrixTotal());
                commande.setProduits(linkCommandeProduits);
                
                commandeDAO.mettreAJourCommande(commande);

                // Vider le panier après validation
                session.removeAttribute("panier");

                response.setStatus(HttpServletResponse.SC_OK);
                
                // Utilisez l'ID du magasin pour récupérer les informations du magasin depuis la base de données
                Magasin magasin = MagasinDAO.getMagasinById(magasinId);

                // Vérifiez si le magasin a été trouvé
                if (magasin != null) {
                    // Ajoutez le nom et l'adresse du magasin aux attributs de la requête
                    request.setAttribute("nomMagasin", magasin.getNom());
                    request.setAttribute("adresseMagasin", magasin.getAdresse());
                } else {
                    request.setAttribute("nomMagasin", "Magasin inconnu");
                    request.setAttribute("adresseMagasin", "Adresse inconnue");
                }

                // Ajout des informations de la commande aux attributs de la requête
                request.setAttribute("dateCommande", commande.getDateCommande());
                request.setAttribute("jourRetrait", commande.getDateRetrait());
                request.setAttribute("horaireRetrait", commande.getHoraireRetrait());

                // page JSP de succès
                request.getRequestDispatcher("/jsp/commandeValide.jsp").forward(request, response);

            } catch (NumberFormatException | ParseException | ServletException e) {
                e.printStackTrace();
                response.sendRedirect("jsp/error.jsp");
            }
        } else {
            response.sendRedirect("jsp/error.jsp");
        }
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
		List<String> eans = panier.getPanier().values().stream().map(ProduitPanier::getEan).collect(Collectors.toList());
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
}
