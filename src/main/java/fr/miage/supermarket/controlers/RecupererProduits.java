package fr.miage.supermarket.controlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Base64;
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
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Servlet de gestion de la récupération des produits avec redirection vers JSP
 */
public class RecupererProduits extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecupererProduits() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getParameter("ean") == null) {
			displayAllProduits(request, response);
		} else {
			displaySpecificProduit(request, response);
		}
		
	}
	
	/**
	 * Affichage du détail d'un produit
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displaySpecificProduit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ProduitDAO produitDAO = new ProduitDAO();
		Produit produit = produitDAO.getProduitById(request.getParameter("ean").toString());
		produit.setImageBase64(imageToBase64(produit.getRepertoireImage()));
		
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
		request.getRequestDispatcher("/jsp/detailProduit.jsp").forward(request, response);
	}
	
	/**
	 * Affichage de l'intégralité des produits
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayAllProduits(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("categorie", CategorieCompte.GESTIONNAIRE.name());
		request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	}
	
	/**
	 * Conversion d'une image en base64 pour intégration dans JSP
	 * @param chemin
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String imageToBase64(String chemin) throws FileNotFoundException, IOException {
		if (chemin == null || chemin.isEmpty()) {
	        return null;
	    }

	    File imageFile = new File(getServletContext().getRealPath("WEB-INF/images/" + chemin));
	    if (!imageFile.exists()) {
	        return null;
	    }

	    byte[] imageBytes;
	    try (FileInputStream fis = new FileInputStream(imageFile)) {
	        imageBytes = new byte[(int) imageFile.length()];
	        fis.read(imageBytes);
	    }

	    // Encodage de l'image en Base64
	    return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
	}
}
