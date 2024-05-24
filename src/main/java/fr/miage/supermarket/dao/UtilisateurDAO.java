package fr.miage.supermarket.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Utilisateur;
import fr.miage.supermarket.utils.HibernateUtil;

public class UtilisateurDAO {
	
	private SessionFactory sessionFactory;
	
	public UtilisateurDAO() {
		this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
	}
	
	//On récupère un object utilisateur d'apres le mail
	public Utilisateur getUtilisateurByMail(String mail) {
		Session session = sessionFactory.getCurrentSession();
		
        try {
            session.beginTransaction();
            Utilisateur utilisateur = (Utilisateur) session.createQuery("FROM Utilisateur WHERE mail = :mail")
                    .setParameter("mail", mail)
                    .uniqueResult();
            session.getTransaction().commit();
            return utilisateur;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
        	finally {
            session.close();
        }
    }
	
	//Ajout d'un utilisateur dans la base
	public void insertUtilisateur(Utilisateur utilisateur) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			System.out.println("Connexion réussie");
	        session.save(utilisateur);
	        session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Connexion failed");
		} finally {
			session.close();
		}
		
	}
	
	//Conversion de chaque caractère du mot de passe en ascii
	public String hacherMotdePasse(String password) {
		StringBuilder hash = new StringBuilder();
        
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            int asciiValue = (int) c;
            hash.append(asciiValue+" ");
        }
		return hash.toString().trim();
	}
	
	public Utilisateur mettreAJourUtilisateur(Utilisateur commande) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(commande);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return commande;
    }
}
