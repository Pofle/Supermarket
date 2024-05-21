package fr.miage.supermarket.controlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import fr.miage.supermarket.dao.LinkListeProduitDAO;
import fr.miage.supermarket.dao.MemoDAO;
import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.Memo;
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
					// Récupérer l'utilisateur connecté
                    HttpSession session = request.getSession();
                    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
                    int utilisateurId = utilisateur.getId();
                    // Récupérer les liste de course et les memos de cet utilisateur
                    List<ShoppingList> shoppingLists = ShoppingListDAO.getShoppingLists(utilisateurId);                    
                    String xmlMemosIdString = convertMemosToXml(shoppingLists);
                    
                    // Ajout des listes de courses et du xml_memo comme attribut de la requête
                    request.setAttribute("shoppingLists", shoppingLists);
                    request.setAttribute("memosIdXml", xmlMemosIdString);
                    
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
			default:
				url = "accueil";
			
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	private String convertMemosToXml(List<ShoppingList> shoppingLists) {
		 List<Memo> memosList = null;
		 
		 try {
			 
		        memosList = MemoDAO.getMemos(shoppingLists);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }		
		
	    StringBuilder xmlBuilder = new StringBuilder();
	    // Initialisation d'un set pour ne pas avoir de doublon sur les noeuds
	    Set<Integer> listeIdUniques = new HashSet<>();
	    
	    xmlBuilder.append("<memos>");
	    for (Memo memo : memosList) {
	    	int listId = memo.getShoppingList().getId();
	    	if (listeIdUniques.add(listId)) {
	            xmlBuilder.append("<memo>");
	            xmlBuilder.append("<id_liste>").append(listId).append("</id_liste>");
	            xmlBuilder.append("</memo>");
	        }
	    }
	    xmlBuilder.append("</memos>");
	    
	    // Enregistrer le XML dans un fichier POUR TEST A SUPPRIMER APRES
	    String xmlString = xmlBuilder.toString();
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Pauline\\Cours\\Projet\\memos_id.xml"))) {
	        writer.write(xmlString);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return xmlString;
	}

}