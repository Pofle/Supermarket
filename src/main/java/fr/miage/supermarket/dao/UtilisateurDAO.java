package fr.miage.supermarket.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import fr.miage.supermarket.models.Utilisateur;
import fr.miage.supermarket.utils.HibernateUtil;

public class UtilisateurDAO {

	public String getMotdepasseByID(int idUser) {
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		
		try {
			Utilisateur utilisateur = session.get(Utilisateur.class, idUser);
			session.getTransaction().commit();
			return utilisateur.getMotdepasse();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public int getIdByMail(String mail) {
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		
		try {
			Utilisateur utilisateur = session.get(Utilisateur.class, mail);
			session.getTransaction().commit();
			return utilisateur.getId();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return (Integer) null;
		} finally {
			session.close();
		}
	}
	
	public void insertUtilisateur(Utilisateur utilisateur) {
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
        session.save(utilisateur);
        // Commit la transaction
        session.getTransaction().commit();
        session.close();
		
	}
	
	//Conversion de chaque caractère du mot de passe en ascii
	public String hacherMotdePasse(String password) {
		StringBuilder hash = new StringBuilder();
        
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            int asciiValue = (int) c;
            hash.append(asciiValue);
        }
		return hash.toString().trim();
	}
}
