package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.models.Magasin;

/**
 * Servlet implementation class ServletPanier
 */
public class ServletPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private MagasinDAO magasinDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPanier() {
        super();
        this.magasinDAO = new MagasinDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Magasin> magasins = magasinDAO.getAllMagasins();
		request.setAttribute("magasins", magasins);
		request.getRequestDispatcher("/jsp/afficherPanier.jsp").forward(request, response);
	}
}
