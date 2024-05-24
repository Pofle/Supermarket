package fr.miage.supermarket.controlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import fr.miage.supermarket.dao.LinkListeProduitDAO;
import fr.miage.supermarket.models.LinkListeProduit;

/**
 * Servlet implementation class GenerationListeProduitXml
 * @author Pauline
 */
public class GenerationListeProduitXml extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerationListeProduitXml() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Methode pour généré un XML des produits contenu dans une liste, depuis un XML global en back
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author Pauline
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	response.setContentType("text/xml; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        Integer listeId = getIntegerParameter(request, "id");

	        try {
	            List<LinkListeProduit> produits = LinkListeProduitDAO.getLinkListeProduitByListeId(listeId);

	            // Création du contenu XML
	            StringBuilder xmlContent = new StringBuilder();
	            xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	            xmlContent.append("<produits>");

	            for (LinkListeProduit produit : produits) {
	                xmlContent.append("<produit>");
	                xmlContent.append("<ean>").append(StringEscapeUtils.escapeXml11(produit.getProduit().getEan())).append("</ean>");
	                xmlContent.append("<libelle>").append(StringEscapeUtils.escapeXml11(produit.getProduit().getLibelle())).append("</libelle>");
	                xmlContent.append("<marque>").append(StringEscapeUtils.escapeXml11(produit.getProduit().getMarque())).append("</marque>");
	                xmlContent.append("<quantite>").append(produit.getQuantite()).append("</quantite>");
	                xmlContent.append("</produit>");
	            }
	            xmlContent.append("</produits>");
	            // Écriture du contenu XML dans la réponse
	            out.println(xmlContent.toString());

	            // Log de contrôle de la génération du XML
	            System.out.println("XML_LinkListeProduits response generated");

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        } finally {
	            out.close();
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
