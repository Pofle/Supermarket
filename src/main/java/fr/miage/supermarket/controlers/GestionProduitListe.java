package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.LinkListeProduitDAO;

/**
 * Servlet des services liés à la gestions des produits dans les listes
 * @author Pauline
 */
public class GestionProduitListe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionProduitListe() {
        super();
    }
    
    /**
     * Methode pour supprimer un prudit de la liste de course
     * @author Pauline
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String actionType = request.getParameter("type_action");        
        if ("delete_produit".equals(actionType)) {
            String eanProduit = request.getParameter("produit_ean");
            String listeIdStr = request.getParameter("listeId");
            Integer listeId = null;
            try {
                listeId = Integer.parseInt(listeIdStr);
            } catch (NumberFormatException e) {
                System.out.println("Erreur lors de la conversion de l'ID de liste en entier : " + e.getMessage());
            }
            
            if (eanProduit != null && listeId != null) {
                LinkListeProduitDAO.supprimerProduit(eanProduit, listeId.intValue());
            }
        }
        response.sendRedirect("central?type_action=gestion_List");
    }
    
    /**
     * Methode pour modifier les quantité de protuits dans une liste
     * @author Pauline
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Récupération de l'ID de la liste
        String listeIdStr = request.getParameter("listeId");
        if (listeIdStr == null) {
            response.sendRedirect("central?type_action=gestion_List");
            return;
        }
        
        Integer listeId = null;
        try {
            listeId = Integer.parseInt(listeIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("central?type_action=gestion_List");
            return;
        }

        // Traitement des autres paramètres
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (!paramName.equals("listeId")) {
                String paramValue = request.getParameter(paramName);
                try {
                    int newQuantity = Integer.parseInt(paramValue);
                    LinkListeProduitDAO.updateProduitsListe(listeId, paramName, newQuantity);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }       
        // Redirection vers la pages des listes de courses
        response.sendRedirect("central?type_action=gestion_List");
    }
}
    	
    	
    	
    	
    




    
	



