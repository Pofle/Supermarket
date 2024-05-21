package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.models.Utilisateur;

/**
 * Servlet qui gere la generation des listes de course de l'utilisatueur en XML
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
    }

	/**
	 * Methode qui recoit la reponse d'une requete en BD des listes de courses de l'utilisateur connecte et qui la convertit en XML de liste de courses
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author Pauline
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		// Récupérer l'utilisateur connecté
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        int utilisateurId = utilisateur.getId();

	   	   
	    List<ShoppingList> shoppingLists = null;
	    try {
	        shoppingLists = ShoppingListDAO.getShoppingLists(utilisateurId);
	        System.out.println("Shopping lists fetched successfully");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    String xmlContent = convertShoppingListsToXml(shoppingLists);
	    
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
}


