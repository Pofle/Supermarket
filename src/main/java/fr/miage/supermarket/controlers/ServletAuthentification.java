package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.UtilisateurDAO;
import fr.miage.supermarket.models.Utilisateur;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServletAuthentification extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAuthentification() {
		// TODO Auto-generated constructor stub
    	super();
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        UtilisateurDAO user = new UtilisateurDAO();
        int idUser = user.getIdByMail(mail);
        String mdpUser = user.getMotdepasseByID(idUser);
        String hashedPassword = user.hacherMotdePasse(password);

        // Vérification de l'utilisateur
        if (mdpUser.equals(hashedPassword)) {
            request.setAttribute("message", "Authentification réussie ! Bienvenue, " + mail + ".");
        } else {
            request.setAttribute("message", "Échec de l'authentification. Veuillez réessayer.");
        }

        // Redirection vers la page JSP avec le message approprié
        request.getRequestDispatcher("/SupermarketG3/login.jsp").forward(request, response);
    }

}