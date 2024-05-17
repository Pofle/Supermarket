package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.ShoppingListDAO;
/**
 * Servlet gérant les listes de course
 * @author PaulineF
 */
public class ServletListeCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletListeCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}

	/**
	 * Méthode DoPost pour ajouter une liste
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author PaulineF
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomListeCourse = request.getParameter("inputName");
        if (nomListeCourse != null && !nomListeCourse.trim().isEmpty()) {
            ShoppingListDAO.ajouterListe(nomListeCourse);

            response.sendRedirect("central?type_action=gestion_List");
        }
    }

}
