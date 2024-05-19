package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.Promotion;

/**
 * Servlet de gestion de la récupération du détail d'un produit
 * @author EricB
 */
public class RecupererProduits extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProduitDAO produitDao;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecupererProduits() {
		super();
		this.produitDao = new ProduitDAO();
	}

	/**
	 * Méthode GET permettant de récupérer sous forme un produit en fonction de son EAN et de l'afficher à l'aide d'une JSP </br>
	 * Paramètre ean obligatoire, correspondant à l'EAN du produit dont consulter le détail
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @author EricB
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Produit produit = produitDao.getProduitByEan(request.getParameter("ean").toString());
		produit.setImageBase64(getServletContext().getRealPath("WEB-INF/images/"));
		
		Date now = new Date();
		List<Promotion> promotionsDisponibles = new ArrayList<>();
		for(Promotion prom: produit.getPromotions()) {
			if(prom.getDateDebut().before(now) && prom.getDateFin().after(now)) {
				promotionsDisponibles.add(prom);
			}
		}

		request.setAttribute("categorie", CategorieCompte.GESTIONNAIRE.name());
		request.setAttribute("produit", produit);
		request.setAttribute("promotions", promotionsDisponibles);
		request.getRequestDispatcher("/WEB-INF/jsp/detailProduit.jsp").forward(request, response);
	}

	

}
