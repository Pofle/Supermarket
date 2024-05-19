package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.http.*;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.dao.PromotionDAO;
import fr.miage.supermarket.models.*;

/**
 * Servlet de gestion du panier.
 * 
 * @author EricB
 */
public class AjoutProduitPanier extends HttpServlet {

	private ProduitDAO produitDAO;
	private PromotionDAO promotionDAO;

	public AjoutProduitPanier() {
		this.produitDAO = new ProduitDAO();
		this.promotionDAO = new PromotionDAO();
	}

	/**
	 * Méthode GET permettant de récupérer sous forme de XML des informations sur les produits contenus dans le panier en session.
	 * Avec le paramètre displayOption renseigné à "all", renvoit l'ensemble des produits contenus en session, et le prix total du panier.
	 * Sans le paramètre displayOption renseigné à "all", renvoit le nombre de produits enregistrés dans le panier.
	 * 
	 * @see {@link HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)};
	 * 
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
	 * @author EricB
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");

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

			response.getWriter()
					.write("<response><nombreProduits>" + panier.getPanier().size() + "</nombreProduits></response>");
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	/**
	 * Ajoute un produit dans le panier en session s'il n'existe pas déjà.
	 * 
	 * @param panier le panier contenu en session
	 * @param ean l'ean du produit à ajouter
	 * @param response la réponse donnée par le servlet
	 * @author EricB
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

	/**
	 * Construit une chaîne de caractères représentant la réponse en XML contenant
	 * l'ensemble des produits contenus en session dans le panier
	 * 
	 * @param panier le panier contenu en session
	 * @return la chaîne de caractères
	 * @author EricB
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
}
