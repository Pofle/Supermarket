package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.UtilisateurDAO;
import fr.miage.supermarket.models.Utilisateur;

/**
 * Servlet implementation class ServletInscription
 */
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInscription() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/inscription.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Récupération des paramètres
		String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");
        
        //Création d'une instance d'utilisateur
        Utilisateur nouvelUtilisateur = new Utilisateur();
        UtilisateurDAO user = new UtilisateurDAO();
        String hashedPassword = user.hacherMotdePasse(password);

        //Attribution des attributs et insertion en BDD
        nouvelUtilisateur.setPrenom(prenom);
        nouvelUtilisateur.setNom(nom);
        nouvelUtilisateur.setMail(mail);
        nouvelUtilisateur.setMotdepasse(hashedPassword);
        user.insertUtilisateur(nouvelUtilisateur);
        request.setAttribute("message", "Inscription réussie! Bienvenue "+prenom+" "+nom+".");
        
        request.getRequestDispatcher("/jsp/inscription.jsp").forward(request, response);
	}

}
