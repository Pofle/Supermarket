package fr.miage.supermarket.controlers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import fr.miage.supermarket.dao.LinkListeProduitDAO;
import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.ListWrapper;
import fr.miage.supermarket.models.Utilisateur;


/**
 * Servlet principale qui implemente la classe ServletDispatcher
 *
 */
public class ServletDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur par défaut
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDispatcher() {
		super();
		 System.out.println("ServletDispatcher initialized");
	}

	/**
	 * Gère les requêtes HTTP GET.
	 * @param request L'objet HttpServletRequest contenant la requête
	 * @param response L'objet HttpServletResponse contenant la réponse envoyée
	 * @throws ServletException Si une erreur survient au niveau du servlet
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("type_action");
		
		////// ---> Moyen temporaire de changer d'utilisateur au travers de cette variable. <---\\\\\
		//String categorieCompte = CategorieCompte.VISITEUR.name();
		// FIN
		
		HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        String categorieCompte = CategorieCompte.GESTIONNAIRE.name();
        if (user != null) {
        	categorieCompte = user.getRole().name();
		}
		
		if (categorieCompte != null) {
			if (categorieCompte.equals(CategorieCompte.GESTIONNAIRE.name())) {
				dispatchGestionnaireFuncs(action, request, response);
			}
			if (categorieCompte.equals(CategorieCompte.PREPARATEUR.name())) {
				dispatchPreparateurFuncs(action, request, response);
			}
			if (categorieCompte.equals(CategorieCompte.UTILISATEUR.name())) {
				dispatchUtilisateurFuncs(action, request, response);
			} else {
				dispatchDefaultFuncs(action, request, response);
			}
		}
	}

	/**
	 * Gère les fonctionnalités spécifiques aux gestionnaires.
	 * @param action L'action à effectuer
	 * @param request L'objet HttpServletRequest contenant la requête
	 * @param response L'objet HttpServletResponse contenant la réponse envoyée
	 * @throws ServletException Si une erreur survient au niveau du servlet
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 */
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
	
	/**
	 * Gère les fonctionnalités spécifiques aux préparateurs.
	 * @param action L'action à effectuer
	 * @param request L'objet HttpServletRequest contenant la requête
	 * @param response L'objet HttpServletResponse contenant la réponse envoyée
	 * @throws ServletException Si une erreur survient au niveau du servlet
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 */
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

	/**
	 * Gère les fonctionnalités par défaut pour les visiteurs.
	 * @param action L'action à effectuer
	 * @param request L'objet HttpServletRequest contenant la requête
	 * @param response L'objet HttpServletResponse contenant la réponse envoyée
	 * @throws ServletException Si une erreur survient au niveau du servlet
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 */
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
                url = "login";
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
	
	/**
	 * Gère les fonctionnalités spécifiques aux utilisateurs connectés.
	 * @param action L'action à effectuer
	 * @param request L'objet HttpServletRequest contenant la requête
	 * @param response L'objet HttpServletResponse contenant la réponse envoyée
	 * @throws ServletException Si une erreur survient au niveau du servlet
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 */
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
				// TODO:: remplacer par le get de l'id de l'utilisateur connecté
					// 
					int userId = 1;
					//
				// FIN TODDO
					List<ShoppingList> shoppingLists = ShoppingListDAO.getShoppingLists(userId);
					for (ShoppingList shoppingList : shoppingLists) {
                        shoppingList.getUtilisateur();
                    }
					 request.setAttribute("shoppingLists", shoppingLists);
					 request.getRequestDispatcher("gestionList").forward(request, response);
					return;
				}catch(Exception e) {
					request.setAttribute("msgError", e.getMessage());
					 e.printStackTrace();
				}
				break;
				
			case "rayons":
                url = "rayons";
                break;
            case "promos":
                url = "promos";
                break;
            case "listeCourse":
                url = "listeCourse";
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
