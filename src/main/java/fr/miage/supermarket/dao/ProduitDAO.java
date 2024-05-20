package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Classe de gestion des données pour les produits
 * @author EricB
 */
public class ProduitDAO {

	/**
	 * Se charge d'enregistrer une liste de produits
	 * @param produitsToSave les produits à enregistrer
	 * @author EricB
	 */
	public void registerProduits(List<Produit> produitsToSave) {		
		//Récupération de la session 
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		for(Produit prd: produitsToSave) {
			session.merge(prd);
		}
		session.getTransaction().commit();
		
		//Ferme la session
		session.close();
	}
	
	/**
	 * Se charge de renvoyer l'entièreté des produits présents en base
	 * @return la liste des produits
	 */
	public List<Produit> getAllProduits() {
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		
		try {
			Query<Produit> query = session.createQuery("FROM Produit", Produit.class);
			List<Produit> produits = query.getResultList();
			
			session.getTransaction().commit();
			
			return produits;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public Produit getProduitById(String ean) {
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		
		try {
			Produit produit = session.get(Produit.class, ean);
			session.getTransaction().commit();
			return produit;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
//	public List<Produit> getProduitsStock15ProchainsJours() {
//        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
//        Session session = sessionFactory.getCurrentSession();
//        
//        session.beginTransaction();
//        
//        try {
//            Query<Produit> query = session.createQuery("SELECT DISTINCT p FROM Produit p JOIN FETCH p.linkProduitStocks lps JOIN FETCH lps.stock s WHERE s.date BETWEEN CURRENT_DATE AND CURRENT_DATE + 15", Produit.class);
//            List<Produit> produits = query.getResultList();
//            
//            session.getTransaction().commit();
//            return produits;
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//            return null;
//        } finally {
//            session.close();
//        }
//    }
	
	public List<Object[]> getProduitsStockDate(Date date) {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
        
        session.beginTransaction();
        
        try {
            Query<Object[]> query = session.createQuery(
                "SELECT p.ean, p.libelle, p.prix, :date, COALESCE(SUM(lps.quantite), 0) " +
                "FROM Produit p " +
                "LEFT JOIN p.linkProduitStocks lps " +
                "LEFT JOIN lps.stock s " +
                "WHERE s.dateStock = :date OR s.dateStock IS NULL " +
                "GROUP BY p.ean, p.libelle, p.prix " +
                "ORDER BY COALESCE(SUM(lps.quantite), 0)", Object[].class
            );
            query.setParameter("date", date);
            List<Object[]> results = query.getResultList();
            
            session.getTransaction().commit();
            return results;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

	public List<Object[]> getProduitsStockSerieDates(List<Date> serieDates) {
        List<Object[]> resultatSerie = new ArrayList<>();
        
        for (Date date : serieDates) {
            List<Object[]> resultatDate = getProduitsStockDate(date);
            if (resultatDate != null) {
                resultatSerie.addAll(resultatDate);
            }
        }
        
        return resultatSerie;
    }

}
