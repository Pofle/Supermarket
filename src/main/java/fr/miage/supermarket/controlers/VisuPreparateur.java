package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.Promotion;

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
			System.out.println("Vers JSP listPaniers ");
		} else {
			displaySpecificPanier(request, response);
			System.out.println("Vers JSP preparerPaniers ");

		}
		
	}
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.println("Servlet VisuPreparateur méthode POST ");
		
		if(request.getParameter("id_commande") == null) {
			displayAllCommandes(request, response);
			System.out.println("Vers JSP listPaniers ");
		} else {
			displaySpecificPanier(request, response);
		}
    }
	
	/**
	 * Affichage du détail d'un panier
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
		for(Commande c : commandes) {
			System.out.println("préparation jsp listePaniers visualisation commande " + c.getId_commande());
		}
		System.out.println("");
		// attention set catégorie 
		request.setAttribute("categorie", CategorieCompte.PREPARATEUR.name());
		request.setAttribute("commande", commandes);
		
		request.getRequestDispatcher("/jsp/listePaniers.jsp").forward(request, response);
	}
	
		
}
