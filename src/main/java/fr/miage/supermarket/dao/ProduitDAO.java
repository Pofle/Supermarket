package fr.miage.supermarket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
	
}
