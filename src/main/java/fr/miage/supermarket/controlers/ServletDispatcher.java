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
	String categorieCompte = CategorieCompte.GESTIONNAIRE.name();

		if (categorieCompte != null) {
			switch (categorieCompte) {
            case "GESTIONNAIRE":
                dispatchGestionnaireFuncs(action, request, response);
                break;
            case "PREPARATEUR":
                dispatchPreparateurFuncs(action, request, response);
                break;
            default:
                dispatchDefaultFuncs(action, request, response);
                break;
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
			case "gestionStock":
				url = "gestionStock";
				break;
			case "gestionCommande":
				url = "gestionCommande";
				break;
			case "statistiques":
				url = "statistiques";
				break;
			default:
				url = "accueil";
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	private void dispatchPreparateurFuncs(String action, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url;
		request.setAttribute("categorie", CategorieCompte.PREPARATEUR.name());
		if (action == null)
			url = "accueil";
		else {
			switch (action) {
			case "preparationPanier":
				url = "preparationPanier";
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
            case "rayons":
                url = "rayons";
                break;
            case "promos":
                url = "promos";
                break;
            case "listeCourse":
                url = "listeCourse";
                break;
            case "connexionInscription":
                url = "connexionInscription";
                break;
            case "panier":
                url = "panier";
                break;
            default:
                url = "accueil";
            }
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
