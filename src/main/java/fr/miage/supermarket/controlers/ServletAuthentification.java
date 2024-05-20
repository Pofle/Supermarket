package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        String mail = request.getParameter("mail").trim();
        String password = request.getParameter("password").trim();
        
        //Création d'une instance d'utilisateur pour interroger la BDD, on stocke en local un utilisateur à partir de son mail
        UtilisateurDAO user = new UtilisateurDAO();
        Utilisateur userConnecting = user.getUtilisateurByMail(mail);
        String mdpUser = "";
        String hashedPassword = "";
        if (userConnecting != null) {
        	mdpUser = userConnecting.getMotdepasse();
        	hashedPassword = user.hacherMotdePasse(password);
		}

        // Vérification de l'utilisateur et son MDP
        
        //Si valide, on redirige vers une page de confirmation, et on enregistre l'utilisateur en session HTTP
        if (mdpUser.equals(hashedPassword) && userConnecting != null) {
        	System.out.println("Connexion réussie");
            request.setAttribute("message", "Authentification réussie ! Bienvenue, " + mail + ".");
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", userConnecting);
            request.getRequestDispatcher("/jsp/confirmLogin.jsp").forward(request, response);
        } else {
        	//Si la connexion n'est pas valide, on explique pourquoi et on propose à nouveau à l'utilisateur de se connecter
            if (userConnecting == null) {
            	System.out.println("Il n'existe pas d'utilisateur avec ce mail");
            	request.setAttribute("message", "Il n'existe pas d'utilisateur avec ce mail");
    		} else {
    			System.out.println("Mot de passe incorrect");
    			request.setAttribute("message", "Mot de passe incorrect");
    		}
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }

    }

}