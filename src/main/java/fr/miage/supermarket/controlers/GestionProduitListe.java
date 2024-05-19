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

            // Enregistrement du contenu XML dans un fichier
            String filePath = "C:\\Users\\Pauline\\Cours\\Projet\\produits.xml";
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(xmlContent.toString());
            fileWriter.close();

            // Écriture du contenu XML dans la réponse
            out.println(xmlContent.toString());

            // Log de controle de la generation du xml et de son enregistrement
            System.out.println("XML_LinkListeProduits response generated and saved at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            out.close();
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("Requête POST reçue");

        // Log all parameter names and values
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println("Paramètre reçu: " + paramName + " = " + request.getParameter(paramName));
        }

        // Récupération de l'ID de la liste
        String listeIdStr = request.getParameter("listeId");
        if (listeIdStr == null) {
            System.out.println("Paramètre 'listeId' non trouvé");
            response.sendRedirect("central?type_action=gestion_List");
            return;
        }
        
        Integer listeId = null;
        try {
            listeId = Integer.parseInt(listeIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Erreur de conversion de 'listeId': " + listeIdStr);
            response.sendRedirect("central?type_action=gestion_List");
            return;
        }

        System.out.println("ID de la liste: " + listeId);

        // Traitement des autres paramètres
        parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (!paramName.equals("listeId")) {
                String paramValue = request.getParameter(paramName);
                
                System.out.println("Paramètre - EAN: " + paramName + ", Quantité: " + paramValue);
                
                try {
                    int newQuantity = Integer.parseInt(paramValue);
                    LinkListeProduitDAO.updateProduitsListe(listeId, paramName, newQuantity);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Redirection après le traitement
        response.sendRedirect("central?type_action=gestion_List");
    }


//        response.sendRedirect("central?type_action=gestion_List");
    
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
    	
    	
    	
    	
    




    
	



