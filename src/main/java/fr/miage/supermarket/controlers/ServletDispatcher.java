package fr.miage.supermarket.controlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.models.CategorieCompte;

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
		
		// Moyen temporaire de changer d'utilisateur au travers de cette variable.
		String categorieCompte = CategorieCompte.VISITEUR.name();

		if (categorieCompte != null) {
			if (categorieCompte.equals(CategorieCompte.GESTIONNAIRE.name())) {
				dispatchGestionnaireFuncs(action, request, response);
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
		String url;
		request.setAttribute("categorie", CategorieCompte.VISITEUR.name());
		if (action == null)
			url = "accueil";
		else {
			switch (action) {
			default:
				url = "accueil";
			case "gestion_List":
				url= "/gestionList";
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
