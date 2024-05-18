package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.ShoppingListDAO;
/**
 * Servlet gérant les listes de course
 * @author PaulineF
 */
public class ServletListeCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletListeCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Méthode DoPost pour ajouter une liste
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author PaulineF
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomListeCourse = request.getParameter("inputName");
        if (nomListeCourse != null && !nomListeCourse.trim().isEmpty()) {
            ShoppingListDAO.ajouterListe(nomListeCourse);

            response.sendRedirect("central?type_action=gestion_List");
        }
    }
	
	/**
	 * Methode pour supprimer une liste de course - A COMPLETER AVEC SUPPRESSION DE SES PRODUIT ?
	 * @author Pauline
	 */
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String actionType = request.getParameter("type_action");        
       if ("delete_list".equals(actionType)) {
            Integer listeId = getIntegerParameter(request, "list_id");
            if (listeId != null) {
                ShoppingListDAO.supprimerListe(listeId);
            }
            response.sendRedirect("central?type_action=gestion_List");
            
        }
    }
	
	/**
	 * Method generique pour encapsuler la conversion d'un parametre type INT en STRING
	 * @param request
	 * @param paramName
	 * @return
	 */
	private Integer getIntegerParameter(HttpServletRequest request, String paramName) {
	    String paramValue = request.getParameter(paramName);
	    if (paramValue != null && !paramValue.isEmpty()) {
	        try {
	            return Integer.parseInt(paramValue);
	        } catch (NumberFormatException e) {
	            
	        }
	    }
	    return null; 
	}

}
