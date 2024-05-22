package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.mail.PasswordAuthentication;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.models.LinkCommandeProduit;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowSysOut;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.Promotion;

public class PreparerPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
		/**
		 * @see HttpServlet#HttpServlet()
		 */
	public PreparerPanier() {
		super();
	}
		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
		 *      response)
		 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Servlet PreparerPanier méthode GET ");
		gestionFormu(request, response);
		 System.out.println("vers servlet VisuPreparateur");

	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 System.out.println("Servlet PreparerPanier méthode POST ");
		 gestionFormu(request, response);
	 }
	 
	 CommandeDAO commandeDAO = new CommandeDAO();
	 private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss:SSS");

	 private void gestionFormu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 /*------- paramètres ------*/
		 String[] idLinkValide = request.getParameterValues("produitValide");
		 String prepaChrono = request.getParameter("prepaChrono");
	     String finTempsPrepa = request.getParameter("finTempsPrepa");
	     String valide = "Contenu validé de la commande : ";
	     String manque = "";
	     /*------ Traitement des valeurs et insertions du chrono en bd */
	     ArrayList<LinkCommandeProduit> linkValid = new ArrayList<LinkCommandeProduit>();
	     //vérification que des éléments ont été validé/sélectionné
	     if(idLinkValide != null) {
		     // traitement des éléments qui ont été validé/sélectionné
		        for (String id : idLinkValide) {
		        	String[] ids = id.split(",");
	                String idCommande = ids[0];
	                String ean = ids[1];
					linkValid.add(CommandeDAO.loadLink(idCommande, ean));
				}
		        for(LinkCommandeProduit l : linkValid) {
		        	valide = valide + l.getProduit().getLibelle() + ", ";
		        }
		        valide = valide.substring(0, valide.length()-2);
		        valide = valide + ".";
		        System.out.println("Sélection formulaire, " + valide);
		        // on vérifie si tous les éléments ont été validé/sélectionné
		        System.out.println("gestionFormu - génération de la commande de base pour comparaison");
		        ArrayList<LinkCommandeProduit> linkCompar = commandeDAO.getLinkByCommande(linkValid.get(linkValid.size()-1).getCommande().getId_commande());
				if(linkCompar.size() != linkValid.size()) {
					// on ne garde que les éléments qui n'ont pas été validés
					for(LinkCommandeProduit l : linkValid) {
						for (int i = 0; i < linkCompar.size();i++) {
							if(linkCompar.get(i).getProduit().getEan().equals(l.getProduit().getEan())) {
								linkCompar.remove(linkCompar.get(i));
							}
						}
					}
					manque = "Attention il manque : ";
					for (LinkCommandeProduit l : linkCompar) {
						manque = manque + l.getProduit().getLibelle() + ", " ;
					}
					manque = manque.substring(0, manque.length()-2);
					manque = manque + ".";
					System.out.println(manque);
				}
				
				// Vérification si le chrono a été lancé
		        if (prepaChrono != null && !prepaChrono.isEmpty()) {
		            Date dateDebut = Date.from(Instant.parse(prepaChrono));
		            long debutTemps = dateDebut.getTime();
		            
		            // Vérification si le chrono a été arrêté
		            if (finTempsPrepa != null && !finTempsPrepa.isEmpty()) {
		                Date dateFin = Date.from(Instant.parse(finTempsPrepa));
		                long finTemps = dateFin.getTime();
		                
		                // Calcul de la différence entre le lancement et l'arrêt du chrono
		                long differenceTemps = finTemps - debutTemps;
		                
		                // Conversion pour enregistrement dans la bd 
		                Time chronoPanierTime = new Time(differenceTemps - 3600000);  
		                linkValid.get(0).getCommande().setChrono(chronoPanierTime);  
					    commandeDAO.mettreAJourCommande(linkValid.get(0).getCommande());
					    System.out.println("gestionFormu - commande "+ linkValid.get(0).getCommande().getId_commande()+" chrono : " + linkValid.get(0).getCommande().getChrono() + "enregistrée dans la bd");

		            }
		        }
	        }
	     

	     	/*------ Envoi du mail de notification au client ------*/
	        String to = linkValid.get(0).getCommande().getUtilisateur().getMail(); 
	        String from = "supermarketdai@gmail.com";
	        String host = "smtp.gmail.com";
	        String port = "587";
	        String mdp = "ecxu xbzu lfeu cbgt";
	        String sujet = "Votre commande faite le " + linkValid.get(0).getCommande().getDateCommande()+ " est prête";
	        String corpus = "Bonjour, \n    Votre commande faite le " + linkValid.get(0).getCommande().getDateCommande() + " est prête, elle vous attend pour le " + linkValid.get(0).getCommande().getDateRetrait()+" à " + linkValid.get(0).getCommande().getHoraireRetrait() + ". \n" + valide + "\n"+ manque + "\n \n Au plaisir et à bientôt !";
	        
	        // Propriétés du mail
	        Properties properties = System.getProperties();
	        properties.setProperty("mail.smtp.host", host);
	        properties.setProperty("mail.smtp.ssl.trust", host);
	        properties.setProperty("mail.smtp.port", port);
	        properties.setProperty("mail.smtp.auth", "true");
	        properties.setProperty("mail.smtp.starttls.enable", "true");
	
	        // Authentification de l'expéditeur
	        javax.mail.Authenticator auth = new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(from, mdp);
	            }
	        };
	        Session session = Session.getInstance(properties, auth);
	
	        try {
	        	// Création de l'objet MimeMessage
                Message message = new MimeMessage(session);

                // Paramètres du message
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	            // Sujet du mail
	            message.setSubject(sujet);
	            // Corps du message
	            message.setText(corpus);
	
	            // Envoi du mail
	            Transport.send(message);
	            System.out.println("Message envoyé");
	            
	        } catch (MessagingException mex) {
	            System.out.println("Attention erreur lors de l'envoi du mail : " + mex);
	        }

			System.out.println("Vers servlet VisuPreparateur");
			request.getRequestDispatcher("central?type_action=listePaniers").forward(request, response);		 
			
	 }
	
		
}
