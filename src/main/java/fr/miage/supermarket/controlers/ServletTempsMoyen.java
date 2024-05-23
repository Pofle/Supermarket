package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.models.Commande;

public class ServletTempsMoyen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Servlet PreparerPanier méthode GET ");
		affichageTM(request, response);
		}
	
/*	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.println("Servlet PreparerPanier méthode POST ");
		affichageTM(request, response);
		}
*/
	/**
	 * traitement pour le calcul de la moyenne de temps de préparation 
	 * @author RR
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
 	private void affichageTM(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		//les paramètres 
 		ArrayList<Commande> commandes = CommandeDAO.getCommandeInLink();
 		long somme = 0;
 		String resultat = "00:00:00";
 		// vérification s'il existe des commandes preparées/terminées
	 	if(commandes.size() != 0) {
	 		for (Commande c :commandes) {
		 		Time chrono = c.getChrono();

	 			int heures = chrono.getHours();
                int minutes = chrono.getMinutes();
                int secondes = chrono.getSeconds();
                long chronoInSeconds = heures * 3600 + minutes * 60 + secondes;
                somme += chronoInSeconds;
	 		}
	 		long moyenneResultat = somme / commandes.size();

	        // Formater la durée en HH:mm:ss
	        long hrs = moyenneResultat/3600;
	        long min = (moyenneResultat%3600)/60;
	        long sec = (moyenneResultat%60);

	        resultat =  String.format("%02d:%02d:%02d", hrs, min, sec);
 		}
 		request.setAttribute("moyenne", resultat);
 		System.out.println("Vers jsp visuTempsPrepa");
		request.getRequestDispatcher("jsp/visuTempsPrepa.jsp").forward(request, response);
 	}
}
