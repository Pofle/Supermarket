package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.*;

public class AjoutProduitPanier extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des données du formulaire
        String ean = request.getAttribute("ean").toString();
        String quantiteProduit = request.getAttribute("quantiteProduit").toString();

        ProduitDAO produitDAO = new ProduitDAO();
        Produit produit = produitDAO.getProduitById(ean);
        System.out.println(produitDAO.toString());
        if (produit != null) {
        	// Création d'une instance du produit à ajouter au panier
        	HashMap<String, String> produitMap = new HashMap<String, String>();
        	produitMap.put("ean", ean);
        	produitMap.put("quantite", quantiteProduit);
        	produitMap.put("libelle", produit.getLibelle());
        	
        	// Récupération du panier depuis la session
        	HttpSession session = request.getSession();
        	Panier panier = (Panier) session.getAttribute("panier");
        	
        	// Si le panier n'existe pas en session, on le crée
        	if (panier == null) {
        		panier = new Panier();
        		session.setAttribute("panier", panier);
        	}
        	
        	// Ajout du produit au panier avec la quantité spécifiée
        	panier.ajouterProduit(produitMap);
        	
        	// Envoi d'une réponse HTTP avec un statut 200 OK
        	response.setStatus(HttpServletResponse.SC_OK);
        }

        
    }
}
