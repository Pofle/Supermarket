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
        //Log de controle pour l'id de la liste reçu
        System.out.println("Request received for list ID: " + request.getParameter("id"));

        try {
            List<LinkListeProduit> produits = LinkListeProduitDAO.getAllLinkListProduit();
            System.out.println("Produits: " + produits);

            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<produits>");

            for (LinkListeProduit produit : produits) {
                out.println("<produit>");
                out.println("<libelle>" + produit.getProduit().getLibelle() + "</libelle>");                
                out.println("</produit>");
            }
            out.println("</produits>");
            // Log de controle de la generation du xml
            System.out.println("XML response generated");           
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
    	
    	
    	
    	
    




    
	



