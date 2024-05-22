package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.http.*;

import fr.miage.supermarket.dao.CategorieDAO;
import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.dao.PromotionDAO;
import fr.miage.supermarket.dao.UtilisateurDAO;
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
	private CategorieDAO categorieDAO;

	private Map<String, Float> promotions;

	public ProduitPanierService() {
		this.produitDAO = new ProduitDAO();
		this.promotionDAO = new PromotionDAO();
		this.commandeDAO = new CommandeDAO();
		this.categorieDAO = new CategorieDAO();
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
		if(categorieIdString != null) {
			Integer categorieId = Integer.parseInt(categorieIdString);
		    List<Object[]> produitsWithPromoQuantite = produitDAO.findProduitsWithPromotionsAndPurchaseCountByCategorieId(categorieId);

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
		        xmlResponse.append("<conditionnement>").append(produit.getConditionnement()).append("</conditionnement>");
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
			//response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
                commande.setStatut(StatutCommande.EN_COURS);
                commande.setIdMagasin(magasinId);
                commande.setDateCommande(currentDate);
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
                
                // ID du magasin pour récupérer les informations du magasin depuis la base de données
                Magasin magasin = MagasinDAO.getMagasinById(magasinId);

                // Vérification si le magasin a été trouvé
                if (magasin != null) {
                    // Ajout nom et l'adresse du magasin aux attributs de la requête
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
}
