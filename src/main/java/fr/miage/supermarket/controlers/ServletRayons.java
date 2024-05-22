package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.RayonDAO;
import fr.miage.supermarket.models.Rayon;

/**
 * Servlet de gestion de l'affichage de la page de rayons
 * @author EricB
 */
public class ServletRayons extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RayonDAO rayonDAO;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRayons() {
		super();
		this.rayonDAO = new RayonDAO();
	}

	/**
     * Gère les requêtes HTTP GET en récupérant la liste de tous les rayons depuis la base de données
     * et en les transmettant à la page JSP 'rayons.jsp' pour affichage.
     *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @author EricB
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Rayon> rayons = rayonDAO.getAllRayons();
		request.setAttribute("rayons", rayons);
		request.getRequestDispatcher("/jsp/rayons.jsp").forward(request, response);
	}
}
