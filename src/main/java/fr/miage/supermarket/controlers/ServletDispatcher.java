package fr.miage.supermarket.controlers;

import fr.miage.supermarket.models.Commande;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.CommandeDAO;
//import org.hibernate.mapping.List;

import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.ShoppingList;
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
        String categorieCompte = CategorieCompte.VISITEUR.name();
        if (user != null) {
        	categorieCompte = user.getRole().name();
		}
		
        if (categorieCompte != null) {
            switch (categorieCompte) {
                case "GESTIONNAIRE":
                    dispatchGestionnaireFuncs(action, request, response);
                    break;
                case "PREPARATEUR":
                    dispatchPreparateurFuncs(action, request, response);
                    break;
                case "UTILISATEUR":
                    dispatchUtilisateurFuncs(action, request, response);
                    break;
                default:
                    dispatchDefaultFuncs(action, request, response);
                    break;
            }
        } else {
            dispatchDefaultFuncs(action, request, response);
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
				url = "stocks";
				break;
			case "gestionCommande":
				url = "gestionCommande";
				break;
			case "habitudesConsommation":
				url = "habitudesConsommation";
				break;
			default:
				url = "accueil";
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	/**
	 * Gère les fonctionnalités spécifiques aux préparateurs.
	 * @author RR
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
			case "listePaniers":
				url = "listePaniers";
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
            case "nosRayons":
            	url = "nosRayons";
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
					// Récupérer l'utilisateur connecté
                    HttpSession session = request.getSession();
                    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
                    int utilisateurId = utilisateur.getId();
                    // Récupérer les liste de course de cet utilisateur
                    List<ShoppingList> shoppingLists = ShoppingListDAO.getShoppingLists(utilisateurId);
                    // Ajouter les listes de courses comme attribut de la requête
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
            case "habitudesConsommation":
            	url = "habitudesConsommation";
            	break;
            case "nosRayons":
            	url = "nosRayons";
            	break;
			default:
				url = "accueil";
			
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	

}