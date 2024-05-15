package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import fr.miage.supermarket.models.*;

public class AjoutProduitPanier extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des données du formulaire
        String ean = request.getParameter("ean");
        String libelle = request.getParameter("libelle");
        String prixUnitaire = request.getParameter("prixUnitaire");
        String quantiteProduit = request.getParameter("quantiteProduit");

        // Création d'une instance du produit à ajouter au panier
        HashMap<String, String> produit = new HashMap<String, String>();
        produit.put("ean", ean);
        produit.put("libellé", libelle);
        produit.put("prix", prixUnitaire);
        produit.put("quantité", quantiteProduit);
        
        
        // Récupération du panier depuis la session
        HttpSession session = request.getSession();
        Panier panier = (Panier) session.getAttribute("panier");
        
        // Si le panier n'existe pas en session, on le crée
        if (panier == null) {
            panier = new Panier();
            session.setAttribute("panier", panier);
        }
        
        // Ajout du produit au panier avec la quantité spécifiée
        panier.ajouterProduit(produit);
        
        // Envoi d'une réponse HTTP avec un statut 200 OK
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
