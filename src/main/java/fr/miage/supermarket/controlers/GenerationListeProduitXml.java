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
 * Servlet implementation class GenerationListeProduitXml
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        Integer listeId = getIntegerParameter(request, "id");
        // Log de contrôle pour l'id de la liste reçu
        System.out.println("Request received for list ID: " + listeId);

        try {
            List<LinkListeProduit> produits = LinkListeProduitDAO.getLinkListeProduitByListeId(listeId);

            // Création du contenu XML
            StringBuilder xmlContent = new StringBuilder();
            xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlContent.append("<produits>");

            for (LinkListeProduit produit : produits) {
                xmlContent.append("<produit>");
                xmlContent.append("<ean>").append(produit.getProduit().getEan()).append("</ean>");
                xmlContent.append("<libelle>").append(produit.getProduit().getLibelle()).append("</libelle>");
                xmlContent.append("<marque>").append(produit.getProduit().getMarque()).append("</marque>");
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
