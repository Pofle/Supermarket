package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.LinkListeProduitDAO;
import fr.miage.supermarket.models.LinkListeProduit;

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
     * Methode pour récupérer la liste des produits d'une liste depuis un fichier xml 
     * @author Pauline
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        Integer listeId = getIntegerParameter(request, "id");
        //Log de controle pour l'id de la liste reçu
        System.out.println("Request received for list ID: " + listeId);

        try {
            List<LinkListeProduit> produits = LinkListeProduitDAO.getLinkListeProduitByListeId(listeId);
            System.out.println("Produits: " + produits);

            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<produits>");

            for (LinkListeProduit produit : produits) {
                out.println("<produit>");
                out.println("<libelle>" + produit.getProduit().getLibelle() + "</libelle>");
                out.println("<marque>" + produit.getProduit().getMarque() + "</marque>");
                out.println("<quantite>" + produit.getQuantite() + "</quantite>");
                out.println("</produit>");
            }
            out.println("</produits>");
            // Log de controle de la generation du xml
            System.out.println("XML_LinkListeProduits response generated");   
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    /**
	 * Method generique pourconvertir un parametre type INT en STRING
	 * @param request L'objet HttpServletRequest contenant la requête
	 * @param stringParam parametre string qui sera converti en INT
	 * @return
	 * @author Pauline
	 */
	private Integer getIntegerParameter(HttpServletRequest request, String stringParam) {
	    String paramValue = request.getParameter(stringParam);
	    if (paramValue != null && !paramValue.isEmpty()) {
	        try {
	            return Integer.parseInt(paramValue);
	        } catch (NumberFormatException e) {
	            
	        }
	    }
	    return null; 
	}
}
    	
    	
    	
    	
    




    
	



