package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.models.StatutCommande;
import fr.miage.supermarket.models.Utilisateur;

public class CommandeUtilisateur extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CommandeDAO commandeDAO;
    private MagasinDAO magasinDAO;

    public CommandeUtilisateur() {
        super();
        this.commandeDAO = new CommandeDAO();
        this.magasinDAO = new MagasinDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateurConnecte == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        //liste des commandes
        List<Commande> commandesUtilisateur = commandeDAO.getCommandesByUtilisateur(utilisateurConnecte);
        // mise à jour des statuts des commandes prêtes à terminées
        for (Commande c : commandesUtilisateur) {
        	if(LocalDate.now().isAfter(c.getDateRetrait()) 
        			|| (
        					LocalDate.now().isEqual(c.getDateRetrait()) 
        					&& LocalTime.now().isAfter(LocalTime.parse(c.getHoraireRetrait(),DateTimeFormatter.ofPattern("HH:mm"))))
        			) {
        			c.setStatut(StatutCommande.TERMINE);
        			commandeDAO.mettreAJourCommande(c);
        	}
        }

        // liste des magasins
        List<Magasin> magasins = magasinDAO.getAllMagasins(); 

        request.setAttribute("commandes", commandesUtilisateur);
        request.setAttribute("nonValide", StatutCommande.NON_VALIDE);
        request.setAttribute("enCours", StatutCommande.EN_COURS);
        request.setAttribute("pret", StatutCommande.PRET);
        request.setAttribute("termine", StatutCommande.TERMINE);
        request.setAttribute("magasins", magasins); // Ajouter les magasins comme attribut

        request.setAttribute("commandes", commandesUtilisateur);
        // ajout des magasins comme attribut
        request.setAttribute("magasins", magasins); 

        request.getRequestDispatcher("/jsp/afficherCommande.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
