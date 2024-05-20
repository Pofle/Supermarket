package fr.miage.supermarket.utils;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.UtilisateurDAO;
import fr.miage.supermarket.models.Utilisateur;

public class Affichage10produitsTest {

	public static void main(String[] args) {
		UtilisateurDAO userDAO = new UtilisateurDAO();
		Utilisateur user = userDAO.getUtilisateurByMail("chloe.test@gmail.com");
		CommandeDAO comDAO = new CommandeDAO();
		System.out.println("breakpoint");
		System.out.println(comDAO.getCommandeIdsByUtilisateurId(user.getId()));

	}

}
