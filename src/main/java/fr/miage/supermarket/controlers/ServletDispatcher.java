package fr.miage.supermarket.controlers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import fr.miage.supermarket.dao.LinkListeProduitDAO;
import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.ListWrapper;

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
		String categorieCompte = CategorieCompte.UTILISATEUR.name();
		// FIN
		
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
				// TO-DO:: remplacer par le get de l'id de l'utilisateur connecté
					// 
					int userId = 1;
					//
				// FIN TOD-DO
					List<ShoppingList> allShoppingLists = ShoppingListDAO.getShoppingLists(userId);
					for (ShoppingList shoppingList : allShoppingLists) {
                        shoppingList.getUtilisateur();
                    }
					 request.setAttribute("shoppingLists", allShoppingLists);
					 ConvertirListeProduitXml(request, response);
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
	
	/**
	 * Methode pour convertir la liste de produit reçu en xml et renvoyer vers la page de gestion des listes
	 *@param request L'objet HttpServletRequest contenant la requête
	 * @param response L'objet HttpServletResponse contenant la réponse envoyée
	 * @throws ServletException Si une erreur survient au niveau du servlet
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 * @author Pauline
	 */
	private void ConvertirListeProduitXml(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        List<LinkListeProduit> allLinkListProduits = LinkListeProduitDAO.getAllLinkListProduit();
	        JAXBContext jaxbContext = JAXBContext.newInstance(LinkListeProduit.class, ListWrapper.class);
	        Marshaller marshaller = jaxbContext.createMarshaller();
	        ListWrapper<LinkListeProduit> wrapper = new ListWrapper<>(allLinkListProduits);
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        
	        //response.setContentType("text/xml");	        
	        StringWriter sw = new StringWriter();
	        marshaller.marshal(wrapper, sw);
	        String xmlString = sw.toString();
	        
	     // SAVE FICHIER XML POUR TEST
	        String filePath = "C:\\Users\\Pauline\\Cours\\Projet\\ALLproduitlist.txt";
	        FileWriter writer = new FileWriter(filePath);
	        writer.write(xmlString);
	        writer.close();
	        request.setAttribute("xmlFilePath", filePath);
	      // FIN

	        // Set XMLstring comme attribut de la requête (pour une manipulation AJAX)
	        request.setAttribute("xmlListeProduit", sw.toString());

	        request.getRequestDispatcher("gestionList").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}

}
