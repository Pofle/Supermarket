package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.*;

public class AjoutProduitPanier extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ean = request.getParameter("ean");
		String quantiteProduit = request.getParameter("quantite");

		if (quantiteProduit == null || Integer.parseInt(quantiteProduit) <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		ProduitDAO produitDAO = new ProduitDAO();
		Produit produit = produitDAO.getProduitById(ean);
		if (produit == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		ProduitPanier produitPanier = new ProduitPanier(produit.getLibelle(), ean, Integer.parseInt(quantiteProduit));

		HttpSession session = request.getSession();
		Panier panier = (Panier) session.getAttribute("panier");

		// Si le panier n'existe pas en session, on le crée
		if (panier == null) {
			panier = new Panier();
			session.setAttribute("panier", panier);
		}

		panier.ajouterProduit(produitPanier);

		response.setStatus(HttpServletResponse.SC_OK);
	}
}
