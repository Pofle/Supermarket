package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.LinkListeProduitDAO;

//import org.hibernate.mapping.List;

import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

/**
 * Servlet principale qui implemente la classe ServletDispatcher
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
					int userId = 1;
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
	 * Methode pour convertir la liste de produit reçu en xml
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
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
	        // Set content type to XML
	        response.setContentType("text/xml");

	        // Marshal XML to a StringWriter
	        StringWriter sw = new StringWriter();
	        marshaller.marshal(wrapper, sw);

	        // Set XML string as request attribute
	        request.setAttribute("xmlData", sw.toString());

	        request.getRequestDispatcher("gestionList").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}

}
