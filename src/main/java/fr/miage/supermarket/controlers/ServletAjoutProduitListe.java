package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.LinkListeProduitDAO;

/**
 * Servlet implementation class ServletAjoutProduitListe
 */
public class ServletAjoutProduitListe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAjoutProduitListe() {
        super();
    }

	/**
	 * Methode qui permet j'aout d'un produit et d'une quantité dans une liste de course
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author Pauline
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionType = request.getParameter("type_action");

        if ("add_produit".equals(actionType)) {
            String eanProduit = request.getParameter("produit_ean");
            String listeIdStr = request.getParameter("select-liste");
            String quantiteStr = request.getParameter("quantite");

            Integer listeId = null;
            Integer quantite = null;
            try {
                listeId = Integer.parseInt(listeIdStr);
                quantite = Integer.parseInt(quantiteStr);
            } catch (NumberFormatException e) {
                System.out.println("Erreur lors de la conversion des paramètres en entier : " + e.getMessage());
                return;
            }

            if (eanProduit != null && listeId != null && quantite != null) {
                LinkListeProduitDAO.ajouterProduitListe(listeId, eanProduit, quantite);
                System.out.println("Product added to the shopping list in DB");
            }
        }

        response.sendRedirect("central?type_action=accueil");
    }
	
}


