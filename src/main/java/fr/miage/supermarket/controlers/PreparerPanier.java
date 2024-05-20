package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowSysOut;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Link_Commande_Produit;
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
		 System.out.println("vers servlet VisuPreparateur");
	 }
	 
	 private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss:SSS");

	 private void gestionFormu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String[] idLinkValide = request.getParameterValues("produitValide");
		 String prepaChrono = request.getParameter("prepaChrono");
	     String finTempsPrepa = request.getParameter("finTempsPrepa");
	     System.out.println(idLinkValide.length);
	     ArrayList<Link_Commande_Produit> linkValid = new ArrayList<Link_Commande_Produit>();
	     if(idLinkValide != null) {
		        for (String id : idLinkValide) {
		        	String[] ids = id.split(",");
	                String idCommande = ids[0];
	                String ean = ids[1];
					linkValid.add(CommandeDAO.loadLink(idCommande, ean));
				}
		        System.out.println("gestionFormu - génération de la commande de base pour comparaison");
				ArrayList<Link_Commande_Produit> linkCompar = CommandeDAO.getLinkByCommande(linkValid.get(linkValid.size()-1).getCommande().getId_commande());
				if(linkCompar.size() != linkValid.size()) {
					linkCompar.removeAll(linkValid);
					System.out.println("Attention il manque : ");
					for (Link_Commande_Produit l : linkCompar) {
						System.out.println(" - " + l.getProduit().getLibelle());
					}
				}
//				//on vérifie que le chrono a été lancé 
//				if(prepaChrono != null && !prepaChrono.isEmpty()) {
//					Date dateDebut = Date.from(Instant.parse(prepaChrono));
//					long debutTemps = dateDebut.getTime();			
//					// on vérifie que le chrono a été arrêté 
//					if (finTempsPrepa != null && !finTempsPrepa.isEmpty()) {
//						Date dateFin = Date.from(Instant.parse(finTempsPrepa));
//						long FinTemps = dateFin.getTime();
//						
//						// on fait la différence entre le lancement et l'arrêt du chrono
//						long differenceTemps = FinTemps - debutTemps;
//						
//						//conversion pour insertion en bd 
//						Instant instant = Instant.ofEpochMilli(differenceTemps);			        
//					    LocalDateTime chronoPanier = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//	
//					    linkValid.get(0).getCommande().setChrono(chronoPanier);  
//					    CommandeDAO.saveCommandechrono(linkValid.get(0).getCommande());
//					    System.out.println("gestionFormu - commande "+ linkValid.get(0).getCommande().getId_commande()+" enregistrée dans la bd");
//					}
//				}
				
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
		                
		                // Formattage de la différence de temps
		                String chronoPanierFormatted = DF.format(new Date(differenceTemps - 3600000));  // Soustraction d'une heure (3600000 ms) pour ajuster le fuseau horaire
		                System.out.println("Temps de préparation: " + chronoPanierFormatted);
		                
		                // Conversion de la différence de temps en java.sql.Time
		                Time chronoPanierTime = new Time(differenceTemps - 3600000);  // Ajustement pour le fuseau horaire
		                linkValid.get(0).getCommande().setChrono(chronoPanierTime);  
					    CommandeDAO.saveCommandechrono(linkValid.get(0).getCommande());
					    System.out.println("gestionFormu - commande "+ linkValid.get(0).getCommande().getId_commande()+" enregistrée dans la bd");

		            }
		        }
	        }
//			 // Envoyer un email de notification
//	        String to = "utilisateur@example.com"; // Remplacez par l'adresse email du destinataire
//	        String from = "moi@gmail.com"; // Remplacez par votre adresse email
//	        String host = "smtp.example.com"; // Remplacez par votre serveur SMTP
	//
//	        // Propriétés du mail
//	        Properties properties = System.getProperties();
//	        properties.setProperty("mail.smtp.host", host);
//	        properties.setProperty("mail.smtp.port", "587"); // Utiliser le port approprié pour votre serveur SMTP
//	        properties.setProperty("mail.smtp.auth", "true");
//	        properties.setProperty("mail.smtp.starttls.enable", "true");
	//
//	        // Authentification de l'expéditeur
//	        Session session = Session.getDefaultInstance(properties, new Authenticator() {
//	            protected PasswordAuthentication getPasswordAuthentication() {
//	                return new PasswordAuthentication("moi@gmail.com", "haha"); // Remplacez par vos identifiants
//	            }
//	        }
//	        );
	//
//	        try {
//	            // Créer un objet MimeMessage
//	            MimeMessage message = new MimeMessage(session);
	//
//	            // De : adresse de l'expéditeur
//	            message.setFrom(new InternetAddress(from));
	//
//	            // À : adresse du destinataire
//	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	//
//	            // Sujet du mail
//	            message.setSubject("Notification de soumission");
	//
//	            // Corps du message
//	            message.setText("La soumission a été effectuée avec succès à " + submitTimestamp);
	//
//	            // Envoyer le message
//	            Transport.send(message);
//	            System.out.println("Message envoyé avec succès...");
//	        } catch (MessagingException mex) {
//	            mex.printStackTrace();
//	        }
			
			request.getRequestDispatcher("central?type_action=listePaniers").forward(request, response);		 
			
	 }
	
		
}
