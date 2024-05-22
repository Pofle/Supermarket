package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;

/**
 * Servlet relié à la jsp listPaniers.jsp 
 * @author RR
 */
public class VisuPreparateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
		/**
		 * @see HttpServlet#HttpServlet()
		 */
	public VisuPreparateur() {
		super();
	}
		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
		 *      response)
		 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("Servlet VisuPreparateur méthode GET ");
		
		if(request.getParameter("id_commande") == null) {
			displayAllCommandes(request, response);
		} else {
			displaySpecificPanier(request, response);

		}
		
	}
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.println("Servlet VisuPreparateur méthode POST ");
		
		if(request.getParameter("id_commande") == null) {
			displayAllCommandes(request, response);
		} else {
			displaySpecificPanier(request, response);
		}
    }
	
	/**
	 * Affichage du détail d'un panier de la commande dont id_commande a été récupéré
	 * @author RR
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displaySpecificPanier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("displaySpecificPanier - Préparation jsp preparer paniers - affichage de ");
		ArrayList<LinkCommandeProduit> linkAsso = CommandeDAO.getLinkByCommande(Integer.parseInt(request.getParameter("id_commande")));
		
		// attention set catégorie 
		request.setAttribute("categorie", CategorieCompte.PREPARATEUR.name());
		request.setAttribute("linkAsso", linkAsso);

		System.out.println("Vers JSP preparerPaniers ");
		request.getRequestDispatcher("/jsp/preparerPanier.jsp").forward(request, response);
	}
	
	/**
	 * Affichage de l'intégralité des commandes 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayAllCommandes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		ArrayList<Commande> commandes = CommandeDAO.getCommandeTrieInLink();
		System.out.println("");
		// attention set catégorie 
		request.setAttribute("categorie", CategorieCompte.PREPARATEUR.name());
		request.setAttribute("commande", commandes);
		
		request.getRequestDispatcher("/jsp/listePaniers.jsp").forward(request, response);
	}
	
		
}
