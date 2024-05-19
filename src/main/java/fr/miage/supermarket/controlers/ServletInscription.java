package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.UtilisateurDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.Utilisateur;

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
        request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Récupération et clean des paramètres
		String prenom = request.getParameter("prenom").trim();
        String nom = request.getParameter("nom").trim();
        String mail = request.getParameter("mail").trim();
        String password = request.getParameter("password").trim();
        
        //Création d'une instance d'utilisateur, vérification de la disponibilité du mail
        Utilisateur nouvelUtilisateur = new Utilisateur();
        UtilisateurDAO user = new UtilisateurDAO();
        if (user.getUtilisateurByMail(mail)==null) {
        	
        	//Attribution des attributs et insertion en BDD
        	nouvelUtilisateur.setPrenom(prenom);
        	nouvelUtilisateur.setNom(nom);
        	nouvelUtilisateur.setMail(mail);
        	String hashedPassword = user.hacherMotdePasse(password);
        	nouvelUtilisateur.setMotdepasse(hashedPassword);
        	nouvelUtilisateur.setRole(CategorieCompte.UTILISATEUR);
        	user.insertUtilisateur(nouvelUtilisateur);
        	request.setAttribute("message", "Inscription réussie! Bienvenue "+prenom+" "+nom+".");
        	HttpSession session = request.getSession();
        	session.setAttribute("utilisateur", nouvelUtilisateur);
        	request.getRequestDispatcher("/WEB-INF/jsp/confirmLogin.jsp").forward(request, response);
        } else {
        	System.out.println("Mail utilisé");
        	request.setAttribute("message", "Ce mail est deja utilisé");
        	request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp").forward(request, response);
        }

	}

}
