package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.ShoppingList;

/**
 * Servlet implementation class GenerationListeXml
 */
public class GenerationListeXml extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Servlet de gestion du xml des liste de courses appartenant a l'utilisateur connecté
     * @see HttpServlet#HttpServlet()
     * @author Pauline
     */
    public GenerationListeXml() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Methode qui recoit la reponse d'une requete en BD des listes de courses de l'utilisateur connecte et qui la convertit en XML de liste de courses
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author Pauline
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//TO-DO :: remplacer par l'ID de l'User CONNECTÉ QUAND authentifaction sera faite
	// -- Code à remplacer
	    int userId = 1;
	// Fin du code à remplacer
	   	   
	    List<ShoppingList> shoppingLists = null;
	    try {
	        shoppingLists = ShoppingListDAO.getShoppingLists(userId);
	        System.out.println("Shopping lists fetched successfully");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    String xmlContent = convertShoppingListsToXml(shoppingLists);
	    // Save du fichier XML pour TEST
	    saveXmlToFile(xmlContent);
	    
	    // Include XML content in the response
	    response.setContentType("application/xml");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(xmlContent);
	    
	    System.out.println("XMLListes content sent in response");
	}

	/**
	 * Methode pour convertir une List de listes de courses en reponse XML
	 * @param shoppingLists la liste des listes de courses de l'utilisateur
	 * @return
	 * @author Pauline
	 */
	private String convertShoppingListsToXml(List<ShoppingList> shoppingLists) {
	    StringBuilder xmlBuilder = new StringBuilder();
	    xmlBuilder.append("<shoppingLists>");
	    for (ShoppingList list : shoppingLists) {
	        xmlBuilder.append("<shoppingList>");
	        xmlBuilder.append("<id>").append(list.getId()).append("</id>");
	        xmlBuilder.append("<name>").append(list.getName()).append("</name>");
	        xmlBuilder.append("</shoppingList>");
	    }
	    xmlBuilder.append("</shoppingLists>");
	    return xmlBuilder.toString();
	}

	// Save du fichier XML pour TEST
	private void saveXmlToFile(String xmlContent) {
	    try {
	        // Define file path
	        String filePath = "C:\\Users\\Pauline\\Cours\\Projet\\listeUser.xml";
	        
	        // Write XML content to file
	        Files.write(Paths.get(filePath), xmlContent.getBytes());
	        System.out.println("XML content saved to file: " + filePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle exception
	    }

	}}
