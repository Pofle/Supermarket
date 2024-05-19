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
     * @see HttpServlet#HttpServlet()
     */
    public GenerationListeXml() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Assume user ID is 1
	    int userId = 1;
	    String ean = request.getParameter("ean");
	    System.out.println("ean du produit a visualiser :"+ean);
	    // Get shopping lists for the user
	    List<ShoppingList> shoppingLists = null;
	    try {
	        shoppingLists = ShoppingListDAO.getShoppingLists(userId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    String xmlContent = convertShoppingListsToXml(shoppingLists);
	    // Save du fichier XML pour TEST
	    saveXmlToFile(xmlContent);
	    
	    // Redirection vers la page de détails
	    request.getRequestDispatcher("/SupermarketG3/accueil?ean=" + ean).forward(request, response);
	}


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
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle exception
	    }

	}}
