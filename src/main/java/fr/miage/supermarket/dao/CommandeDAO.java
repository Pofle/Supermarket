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

public class CommandeDAO {
/**
 * Gestion des commandes pour préparation de panier
 * @author Ravaka
 */
//	private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	public static void createCommande() {
		//ouverture de la session hibernate 
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		//ouverture d'une transaction 
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Commande PanierUn = new Commande(Timestamp.valueOf("2024-05-10 18:30:00"));
		Commande PanierDeux = new Commande(Timestamp.valueOf("2024-05-15 09:15:00"));
		session.save(PanierUn);
		session.save(PanierDeux);
		System.out.println("Commande créé : " + PanierUn.getIdCommande());
		System.out.println("Commande créé : " + PanierUn.getIdCommande());
		transact.commit();
		session.close();
	}
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
	
	public static ArrayList<Long> AllIDCommande() {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Query query = session.createQuery("SELECT id_commande FROM commande");
		ArrayList<Long> id = (ArrayList<Long>) query.getResultList();
		transact.commit();
		return id;
	}

}
