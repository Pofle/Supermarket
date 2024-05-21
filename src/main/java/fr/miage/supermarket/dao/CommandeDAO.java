package fr.miage.supermarket.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.mysql.cj.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.models.Utilisateur;
import fr.miage.supermarket.utils.HibernateUtil;

public class CommandeDAO {
	private SessionFactory sessionFactory;
	
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

	public List<Commande> getCommandeUtilisateur() {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		try {
			session.beginTransaction();
			Utilisateur utilisateur = (Utilisateur) ((HttpSession) session).getAttribute("Utilisateur");
			List<Commande> listeCommandes = new ArrayList<Commande>();
			List<Integer> listeIdCommandes = getCommandeIdsByUtilisateurId(utilisateur.getId());
			for (int i = 0; i < listeIdCommandes.size(); i++) {
				listeCommandes.add(getCommandeById(listeIdCommandes.get(i)));
			}
			return listeCommandes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public Commande getCommandeById(int commandeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			Commande commande = (Commande) session.createQuery("FROM Commande WHERE id_commande = :commandeId")
					.setParameter("commandeId", commandeId).uniqueResult();
			session.getTransaction().commit();
			return commande;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public List<Integer> getCommandeIdsByUtilisateurId(int utilisateurId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.openSession();
		try {

			String hql = "SELECT commande.id_commande FROM Commande commande WHERE commande.utilisateur.getId() = :utilisateurId";
			org.hibernate.query.Query<Integer> query = session.createQuery(hql, Integer.class);
			query.setParameter("utilisateurId", utilisateurId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
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
}
