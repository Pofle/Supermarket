package fr.miage.supermarket.controlers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
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
     * Methode pour récupérer la liste des produits d'une liste de course depuis un xml de tout les link et retourne un xml
     * @author Pauline
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
    	
    	
    	
    	
    




    
	



