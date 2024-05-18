package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.LinkListeProduitDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GestionArticleListe
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
     * Methode pour récupérer les produits d'une liste de course
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();

        try {
            List<LinkListeProduit> produits = LinkListeProduitDAO.getAllLinkListProduit();

            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<produits>");

            for (LinkListeProduit produit : produits) {
                out.println("<produit>");
                out.println("<libelle>" + produit.getProduit().getLibelle() + "</libelle>");
                
                out.println("</produit>");
            }

            out.println("</produits>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
    	
    	
    	
    	
    




    
	



