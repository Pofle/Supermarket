package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mysql.cj.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Utilisateur;
import fr.miage.supermarket.utils.HibernateUtil;

public class CommandeDAO {
    public void save(Commande commande) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionAnnotationFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(commande);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public List<Commande> getCommandeUtilisateur() {
    	Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        try {
            session.beginTransaction();
            Utilisateur utilisateur = (Utilisateur) ((HttpSession) session).getAttribute("Utilisateur");
            List<Commande> listeCommandes = new ArrayList<Commande>();
            for (int i = 0; i < getCommandeIdsByUtilisateurId(utilisateur.getId()).size(); i++) {
				listeCommandes.add(getCommandeById(i));
			}
            return listeCommandes;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
        	finally {
            session.close();
        }
    }
    
    public Commande getCommandeById(int commandeId) {
    	SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
        try {
            session.beginTransaction();
            Commande commande = (Commande) session.createQuery("FROM Commande WHERE ID_COMMANDE = :commandeId").setParameter("commandeId", commandeId).uniqueResult();
            session.getTransaction().commit();
            return commande;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
        	finally {
            session.close();
        }
    }
    
    public List<Integer> getCommandeIdsByUtilisateurId(int utilisateurId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.openSession();
		try {
            String hql = "SELECT c.id FROM Commande c WHERE c.utilisateur.id = :utilisateurId";
            org.hibernate.query.Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("utilisateurId", utilisateurId);
            return query.getResultList();
        }	catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
        	finally {
            session.close();
        }
    }
}