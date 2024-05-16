package fr.miage.supermarket.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cache.spi.support.SimpleTimestamper;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.utils.HibernateUtil;
/**
 * Gestion des commandes clients pour préparation de panier
 * @author RR
 */
public class CommandeDAO {

// les commandes ne sont plus générables manuellement 
//	private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
//	public static void createCommande() {
//		//ouverture de la session hibernate 
//		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
//		//ouverture d'une transaction 
//		Transaction transact = session.getTransaction();
//		if(!transact.isActive()) {
//			transact = session.beginTransaction();
//		}
//		Commande PanierUn = new Commande(Timestamp.valueOf("2024-05-10 18:30:00"));
//		Commande PanierDeux = new Commande(Timestamp.valueOf("2024-05-15 09:15:00"));
//		Commande PanierTrois = new Commande(Timestamp.valueOf("2024-05-18 18:30:00"));
//		Commande PanierQuatre = new Commande(Timestamp.valueOf("2024-05-12 09:15:00"));
//		session.save(PanierUn);
//		session.save(PanierDeux);
//		session.save(PanierTrois);
//		session.save(PanierQuatre);
//		System.out.println("Commande créé : " + PanierUn.getId_commande());
//		System.out.println("Commande créé : " + PanierUn.getId_commande());
//		transact.commit();
//		session.close();
//	}
	/**
	 * Récupération d'une Commande dans la base de données
	 * @author RR
	 * @param id_Commande
	 * @return la Commande désigné par son ID
	 */
	public static Commande loadCommande(long id_Commande) {
	    Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
	    Transaction transact = session.getTransaction();
	    if(!transact.isActive()) {
	        transact = session.beginTransaction();
	    }
	    try {
	        Commande wantedCommande = session.get(Commande.class, id_Commande);
	        transact.commit();
	        return wantedCommande;
	    } catch (RuntimeException e) {
	        if (transact != null && transact.isActive()) {
	            transact.rollback();
	        }
	        throw e;
	    } finally {
	    	 if (session != null) {
	             session.close();
	         }
	    }
	}
	/**
	 * Récupération des différentes commandes dans la BD 
	 * @author RR
	 * @return ArrayList des commandes dans l'ordre croissant des créneaux
	 */
	public static ArrayList<Commande> AllCommandeTrieParCreneau() {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Query query = session.createQuery("FROM commande ORDER BY creneau ASC", Commande.class);
		ArrayList<Commande> commandes = (ArrayList<Commande>) query.getResultList();
		System.out.println("AllCommandeTrieParCreneau returns : ");
		for (int i = 1; i<commandes.size(); i++) {
			if (commandes.get(i)!= null) {
				System.out.println(commandes.get(i).getId_commande());
			}
		}
		transact.commit();
		return commandes;
	}

}
