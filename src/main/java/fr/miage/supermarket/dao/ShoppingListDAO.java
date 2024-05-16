package fr.miage.supermarket.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Management class for ShoppingList datas - Services
 * @author PaulineF
 */
public class ShoppingListDAO {
	
	 public static List<ShoppingList> getShoppingLists() throws Exception {
	        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	        Transaction tx = null;
	        List<ShoppingList> shoppingLists = new ArrayList<>();

	        try {
	            tx = session.beginTransaction();
	            shoppingLists = session.createQuery("from ShoppingList sl where sl.utilisateur.id = :userId", ShoppingList.class)
                        //TO-DO :: remplacer par l'ID de l'User CONNECTÉ QUAND authentifaction sera faite
	            		// -- Code à remplacer
	            		.setParameter("userId", 11)
	            		// Fin du code à remplacer
                        .list();
	        } catch (Exception e) {
	            if (tx != null) tx.rollback();
	            throw e;
	        } finally {
	            session.close();
	        }

	        return shoppingLists;
	    }
		
}
		
		
	 



