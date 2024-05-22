package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.Panier;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ProduitPanier;


/**
 * Servlet implementation class ServletConversionListe
 */
public class ServletConversionListe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConversionListe() {
        super();
    }

    /**
	 * Methode doPost qui récupère les paramètres du formulaire de la modale liste de course et qui les converti en contenu d'un panier
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author Pauline
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

        // Récupérer l'ID de la liste de courses
        String listeId = request.getParameter("listeId");
        
        // Log de controle
        System.out.println("Request PostConverstion received for the listeId: " +listeId);
        
        if (listeId == null || listeId.isEmpty()) {
            System.out.println("listeId from Servlet conversion is empty or null");
            return;
        }

        // Récupérer les paramètres du formulaire
        Map<String, String[]> parameterMap = request.getParameterMap();
        
        // Récupérer ou initialiser le panier de l'utilisateur
        Panier panier = (Panier) session.getAttribute("panier");
        if (panier == null) {
            panier = new Panier();
        }
        ProduitDAO ProduitDAO = new ProduitDAO();


        // Parcourir les paramètres pour ajouter les produits au panier
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String ean = entry.getKey();
            if (!ean.equals("listeId")) { 
                int quantite = Integer.parseInt(entry.getValue()[0]);

                // Récupérer le produit à partir de l'EAN
                Produit produit = ProduitDAO.getProduitByEan(ean);
                if (produit != null && quantite > 0) {
                    String libelle = produit.getLibelle();
                    Float prix = produit.getPrix();
                    String conditionnement = produit.getConditionnement();
                    Float poids = produit.getPoids();
                    String image = produit.getRepertoireVignette(); 

                    // Creation de l'objet
                    ProduitPanier produitPanier = new ProduitPanier(libelle, ean, quantite, prix, null, conditionnement, poids, image);
                    
                    //log de controle
                    System.out.println("Création du lien Panier/produit: "+ libelle + " Qte :" + quantite +" et prix :"+ prix );

                    // Ajout des ProduitPanier au panier
                    panier.ajouterProduit(produitPanier);
                    
                    // Log de controle
                    System.out.println("Ajout des lignes de produits dans le panier");
                    
                }
            }
        }

        // Save du panier dans la session de l'utilisateur
        session.setAttribute("panier", panier);

        // Redirection vers le panier - NON FONCTIONNEL A CAUSE DE L'ATTRIBUT MAGASIN ???
        //response.sendRedirect("afficherPanier");
        response.sendRedirect("central?type_action=accueil"); // redirection vers l'index en attendant
    }
}
