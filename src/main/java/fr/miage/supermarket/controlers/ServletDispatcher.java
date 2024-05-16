package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.hibernate.mapping.List;

import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.ShoppingList;

/**
 * Servlet implementation class ServletDispatcher
 */
public class ServletDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDispatcher() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("type_action");
		
		////// ---> Moyen temporaire de changer d'utilisateur au travers de cette variable. <---\\\\\
		String categorieCompte = CategorieCompte.UTILISATEUR.name();
		// FIN
		
		if (categorieCompte != null) {
			if (categorieCompte.equals(CategorieCompte.GESTIONNAIRE.name())) {
				dispatchGestionnaireFuncs(action, request, response);
			}
			if (categorieCompte.equals(CategorieCompte.UTILISATEUR.name())) {
				dispatchUtilisateurFuncs(action, request, response);
			} else {
				dispatchDefaultFuncs(action, request, response);
			}
		}
	}

	private void dispatchGestionnaireFuncs(String action, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url;
		request.setAttribute("categorie", CategorieCompte.GESTIONNAIRE.name());
		if (action == null)
			url = "accueil";
		else {
			switch (action) {
			case "gestionProduit":
				url = "gestionProduit";
				break;
			default:
				url = "accueil";
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

	private void dispatchDefaultFuncs(String action, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url ="";
		request.setAttribute("categorie", CategorieCompte.VISITEUR.name());
		if (action == null)
			url = "accueil";
		else {
			switch (action) {
			case "gestion_List":
				try {
					url= "gestionList";
				}catch(Exception e) {
					request.setAttribute("msgError", e.getMessage());
					 e.printStackTrace();
				}
				break;
			default:
				url = "accueil";
			
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	private void dispatchUtilisateurFuncs(String action, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url ="";
		request.setAttribute("categorie", CategorieCompte.UTILISATEUR.name());
		if (action == null)
			url = "accueil";
		else {
			switch (action) {
			case "gestion_List":
				try {
					List<ShoppingList> allShoppingLists = ShoppingListDAO.getShoppingLists();
					//RETOUR CONSOLE
					 System.out.println("Shopping lists retrieved: " + allShoppingLists.size());
					 for (ShoppingList list : allShoppingLists) {
	                        System.out.println("List: " + list.getName());
	                    }
					 //FIN
					 request.setAttribute("shoppingLists", allShoppingLists);
					url= "gestionList";
				}catch(Exception e) {
					request.setAttribute("msgError", e.getMessage());
					 e.printStackTrace();
				}
				break;
			default:
				url = "accueil";
			
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
