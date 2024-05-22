package fr.miage.supermarket.dao;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.utils.HibernateUtil;

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
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.utils.HibernateUtil;
/**
 * Gestion des commandes clients pour préparation de panier
 * @author RR, GL
 */
public class CommandeDAO {

private SessionFactory sessionFactory;
    
    public CommandeDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }
    
    public Commande creerCommande(Commande commande) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(commande);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return commande;
    }
    
    public Commande mettreAJourCommande(Commande commande) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(commande);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return commande;
    }
    
    public void creerLinkCommandeProduit(LinkCommandeProduit linkCommandeProduit) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(linkCommandeProduit);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
	/**
	 * Récupération d'une Commande dans la base de données
	 * @author RR
	 * @param id_Commande
	 * @return la Commande désigné par son ID
	 */
	public static Commande loadCommande(Integer id_Commande) {
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
	 * Récupération des lignes de Link_Commande_Produit concernant une commande spécifique
	 * @author RR
	 * @param idCommande
	 * @return
	 */
	public static ArrayList<LinkCommandeProduit> getLinkByCommande(Integer idCommande) {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Query query = session.createQuery("FROM LinkCommandeProduit lc WHERE lc.commande.id_commande = :idCommande", LinkCommandeProduit.class);
		query.setParameter("idCommande", idCommande);
		ArrayList<LinkCommandeProduit> linkByCommande = (ArrayList<LinkCommandeProduit>) query.getResultList();
		System.out.println("getLinkByCommande returns : ");
		for (LinkCommandeProduit lcp : linkByCommande) {
			if (lcp!= null) {
				System.out.println("Commande : "+lcp.getCommande().getId_commande()+" art : "+lcp.getProduit().getEan() + " * " + lcp.getQuantite());
			}
		}
		transact.commit();
		return linkByCommande;
	}
	/**
	 * Récupère les différentes commandes reliés à LinkCommandeProduit
	 * @author RR
	 * @return liste des commandes relié à l'entité LinkCommandeProduit
	 */
	public static ArrayList<Commande> getCommandeInLink() {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		Query query = session.createQuery("SELECT DISTINCT commande FROM LinkCommandeProduit", Commande.class);
		ArrayList<Commande> idComm = (ArrayList<Commande>) query.getResultList();
		System.out.println("getCommandeTrieInLink returns : ");
		for (int i = 0; i<idComm.size(); i++) {
			System.out.print(" Commande : "+idComm.get(i).getId_commande());
		}
		System.out.println(";");
		transact.commit();
		return idComm;
	}
	/**
	 * Récupère les différentes commandes non traités dans l'ordre croissant de retrait
	 * @author RR
	 * @return liste des commandes non traités de LinkCommandeProduit dans l'ordre croissant de retrait
	 */
	public static ArrayList<Commande> getCommandeTrieInLink() {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transact = session.getTransaction();
		if(!transact.isActive()) {
			transact = session.beginTransaction();
		}
		//on récupère les commandes dans l'ordre croissant des dates et heures (converti en Time) de retrait
		Query query = session.createQuery("SELECT DISTINCT commande FROM LinkCommandeProduit WHERE commande.chrono IS NULL ORDER BY commande.dateRetrait ASC, STR_TO_DATE(commande.horaireRetrait, '%H:%i') ASC", Commande.class);
		ArrayList<Commande> idComm = (ArrayList<Commande>) query.getResultList();
		System.out.println("getCommandeTrieInLink returns : ");
		for (int i = 0; i<idComm.size(); i++) {
			System.out.print(" Commande : "+idComm.get(i).getId_commande());
		}
		System.out.println(";");
		transact.commit();
		return idComm;
	}
	/**
	 * Récupère la ligne de LinkCommandeProduit contenant la commande et produit dont les id sont en paramètre
	 * @author RR
	 * @param id_Commande
	 * @param ean
	 * @return LinkCommandeProduit (Commande,PRoduit,Quantite)
	 */
	public static LinkCommandeProduit loadLink(String id_Commande, String ean) {
	    Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
	    Transaction transact = session.getTransaction();
	    if(!transact.isActive()) {
	        transact = session.beginTransaction();
	    }
	    try {
//	        Link_Commande_Produit wantedLink = session.get(Link_Commande_Produit.class, id_commande);
			Query query = session.createQuery("FROM LinkCommandeProduit WHERE commande.id_commande = :id_Commande AND produit.ean = :ean", LinkCommandeProduit.class);
			query.setParameter("id_Commande", id_Commande);
			query.setParameter("ean", ean);
			LinkCommandeProduit wantedLink = (LinkCommandeProduit) query.getSingleResult();
			System.out.println("loadLink returns : cmde " + wantedLink.getCommande().getId_commande() + ", prod " + wantedLink.getProduit().getEan() + ", qte " + wantedLink.getQuantite());
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
