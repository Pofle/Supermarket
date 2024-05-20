package fr.miage.supermarket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Categorie;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.HibernateUtil;

public class CategorieDAO {
	
	private SessionFactory sessionFactory;

    public CategorieDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }

    public Categorie findByLibelle(String libelle) {
        try (Session session = sessionFactory.openSession()) {
            Query<Categorie> query = session.createQuery("FROM Categorie WHERE libelle = :libelle", Categorie.class);
            query.setParameter("libelle", libelle);
            return query.uniqueResult();
        }
    }

    public void save(Categorie categorie) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(categorie);
            transaction.commit();
        }
    }
    
    public List<Categorie> getAllCategorie() {
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		
		try {
			Query<Categorie> query = session.createQuery("FROM Categorie", Categorie.class);
			List<Categorie> categories = query.getResultList();
			
			session.getTransaction().commit();
			
			return categories;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
}
