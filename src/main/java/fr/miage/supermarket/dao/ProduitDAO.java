package fr.miage.supermarket.dao;

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
}
