package fr.miage.supermarket.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.models.Utilisateur;
import fr.miage.supermarket.utils.HibernateUtil;

public class CommandeDAO {

	private SessionFactory sessionFactory;
    
    public CommandeDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }
    
    public List<Commande> getAllCommandes() {
        Session session = sessionFactory.openSession();
        List<Commande> commandes = null;
        try {
            commandes = session.createQuery("FROM Commande", Commande.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return commandes;
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
    
    public List<Commande> getCommandesByUtilisateur(Utilisateur utilisateur) {
        Session session = sessionFactory.openSession();
        List<Commande> commandes = null;
        try {
            String hql = "FROM Commande WHERE utilisateur = :utilisateur";
            Query<Commande> query = session.createQuery(hql, Commande.class);
            query.setParameter("utilisateur", utilisateur);
            commandes = query.getResultList();
        } finally {
            session.close();
        }
        return commandes;
    }
}
