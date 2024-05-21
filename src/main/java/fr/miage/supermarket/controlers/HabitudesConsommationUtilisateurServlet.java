package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.StatistiquesDAO;
import fr.miage.supermarket.models.Utilisateur;

/**
 * Servlet implementation class HabitudesConsommationUtilisateurServlet
 */
public class HabitudesConsommationUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private StatistiquesDAO statistiquesDAO;
	
	public HabitudesConsommationUtilisateurServlet() {
		super();
		this.statistiquesDAO = new StatistiquesDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur == null) {
            response.sendRedirect("connexion");
            return;
        }

        Map<String, Object> statistiques = statistiquesDAO.getStatistiquesConsommation(utilisateur);
        request.setAttribute("statistiques", statistiques);
        request.getRequestDispatcher("/jsp/statistiquesConsommation.jsp").forward(request, response);
	}
}
