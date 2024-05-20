package fr.miage.supermarket.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cache.spi.support.SimpleTimestamper;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Link_Commande_Produit;
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
	public static void saveCommandechrono(Commande commande) {
		//ouverture de la session hibernate 
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		//ouverture d'une transaction 
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		session.merge(commande);
		System.out.println("Commande màj : ID " + commande.getId_commande()+ ", chrono : " + commande.getChrono());
		transact.commit();
		session.close();
	}
	/**
	 * Récupération d'une Commande dans la base de données
	 * @author RR
	 * @param id_Commande
	 * @return la Commande désigné par son ID
	 */
	public static Commande loadCommande(String id_Commande) {
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
	public static ArrayList<Commande> CommandeTrieParCreneau() {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Query query = session.createQuery("FROM Commande ORDER BY creneau ASC", Commande.class);
		ArrayList<Commande> commandes = (ArrayList<Commande>) query.getResultList();
		System.out.println("AllCommandeTrieParCreneau returns : ");
		for (int i = 1; i<commandes.size(); i++) {
			if (commandes.get(i)!= null) {
				System.out.print(commandes.get(i).getId_commande()+"  ");
			}
		}
		System.out.println("");
		transact.commit();
		return commandes;
	}
	/**
	 * Récupération des lignes de Link_Commande_Produit concernant une commande spécifique
	 * @param idCommande
	 * @return
	 */
	public static ArrayList<Link_Commande_Produit> getLinkByCommande(String idCommande) {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Query query = session.createQuery("FROM Link_Commande_Produit lc WHERE lc.commande.id_commande = :idCommande", Link_Commande_Produit.class);
		query.setParameter("idCommande", idCommande);
		ArrayList<Link_Commande_Produit> linkByCommande = (ArrayList<Link_Commande_Produit>) query.getResultList();
		System.out.println("getLinkByCommande returns : ");
		for (Link_Commande_Produit lcp : linkByCommande) {
			if (lcp!= null) {
				System.out.println("Commande : "+lcp.getCommande().getId_commande()+" art : "+lcp.getProduit().getEan() + " * " + lcp.getQte());
			}
		}
		transact.commit();
		return linkByCommande;
	}
	
	public static ArrayList<Commande> getCommandeTrieInLink() {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Query query = session.createQuery("SELECT DISTINCT commande FROM Link_Commande_Produit WHERE commande.chrono IS NULL ORDER BY commande.creneau ASC", Commande.class);
		ArrayList<Commande> idComm = (ArrayList<Commande>) query.getResultList();
		System.out.println("getCommandeTrieInLink returns : ");
		for (int i = 0; i<idComm.size(); i++) {
			System.out.print(" Commande : "+idComm.get(i).getId_commande());
		}
		System.out.println(";");
		transact.commit();
		return idComm;
	}
	
	public static ArrayList<Commande> getCommandeNonTraite() {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Query query = session.createQuery("SELECT DISTINCT commande FROM Link_Commande_Produit WHERE commande.chrono = :null", Commande.class);
		query.setParameter("null", null);
		ArrayList<Commande> idComm = (ArrayList<Commande>) query.getResultList();
		System.out.println("getCommandeTrieInLink returns : ");
		for (int i = 0; i<idComm.size(); i++) {
			System.out.print(" Commande : "+idComm.get(i).getId_commande());
		}
		System.out.println(";");
		transact.commit();
		return idComm;
	}
	public static Link_Commande_Produit loadLink(String id_Commande, String ean) {
	    Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
	    Transaction transact = session.getTransaction();
	    if(!transact.isActive()) {
	        transact = session.beginTransaction();
	    }
	    try {
//	        Link_Commande_Produit wantedLink = session.get(Link_Commande_Produit.class, id_commande);
			Query query = session.createQuery("FROM Link_Commande_Produit WHERE commande.id_commande = :id_Commande AND produit.ean = :ean", Link_Commande_Produit.class);
			query.setParameter("id_Commande", id_Commande);
			query.setParameter("ean", ean);
			Link_Commande_Produit wantedLink = (Link_Commande_Produit) query.getSingleResult();
			System.out.println("loadLink returns : cmde " + wantedLink.getCommande().getId_commande() + ", prod " + wantedLink.getProduit().getEan() + ", qte " + wantedLink.getQte());
			transact.commit();
	        return wantedLink;
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
}
