package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.ShoppingList;

/**
 * Servlet gérant les listes de course
 * 
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}

	/**
	 * Méthode DoPost pour ajouter une liste
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @author PaulineF
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nomListeCourse = request.getParameter("inputName");
		if (nomListeCourse != null && !nomListeCourse.trim().isEmpty()) {
			ShoppingListDAO.ajouterListe(nomListeCourse);
		}
		try {
			List<ShoppingList> allShoppingLists = ShoppingListDAO.getShoppingLists();
			request.setAttribute("shoppingLists", allShoppingLists);
		} catch (Exception e) {
			request.setAttribute("msgError", e.getMessage());
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/jsp/gestionList.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String actionType = request.getParameter("type_action");

		if ("delete_list".equals(actionType)) {
			Integer listeId = getIntegerParameter(request, "list_id");
			if (listeId != null) {
				ShoppingListDAO.supprimerListe(listeId);
			}
		}
		
		try {
			List<ShoppingList> allShoppingLists = ShoppingListDAO.getShoppingLists();
			request.setAttribute("shoppingLists", allShoppingLists);
		} catch (Exception e) {
			request.setAttribute("msgError", e.getMessage());
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/jsp/gestionList.jsp").forward(request, response);
	}

	/**
	 * Method generique pour encapsuler la conversion d'un parametre type INT en
	 * STRING
	 * 
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
