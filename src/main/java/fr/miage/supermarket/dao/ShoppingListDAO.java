package fr.miage.supermarket.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.models.Utilisateur;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Classe permettant de gérer les services liés aux liste de courses
 * @author PaulineF
 */
public class ShoppingListDAO {
	
	/**
	 * Methode pour récupérer toutes les listes de courses d'un utlisateur
	 * @return la liste des listes de courses liées à l'utilisateur connecté
	 * @throws Exception
	 * @author PaulineF
	 */
	 public static List<ShoppingList> getShoppingLists() throws Exception {
	        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	        Transaction tx = null;
	        List<ShoppingList> shoppingLists = new ArrayList<>();

	        try {
	            tx = session.beginTransaction();
	            shoppingLists = session.createQuery("from ShoppingList sl where sl.utilisateur.id = :userId", ShoppingList.class)
                        //TO-DO :: remplacer par l'ID de l'User CONNECTÉ QUAND authentifaction sera faite
	            		// -- Code à remplacer
	            		.setParameter("userId", 1)
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
	 
	 /**
	  * Méthode permettant à un utilisateur connecté de créer une nouvelle liste
	  * @param nomListe, le nom de la liste à ajouter
	  */
	 public static void ajouterListe(String nomListe)
	 {
		 Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	     Transaction tx = null;
	     
	   //TO-DO :: remplacer par l'User CONNECTÉ QUAND authentifaction sera faite
 		// -- Code à remplacer
	    Utilisateur utilisateur = session.get(Utilisateur.class, 1);
	 // Fin du code à remplacer
	    
	     try {
	    	 tx=session.beginTransaction();
	    	 ShoppingList listeCourse = new ShoppingList();
	    	 listeCourse.setName(nomListe);
	    	 listeCourse.setUtilisateur(utilisateur);
	    	 session.save(listeCourse);
	    	 
	    	 tx.commit();
	     } catch (Exception e) {
	            if (tx != null) tx.rollback();
	            throw e;
	        } finally {
	            session.close();
	        }
	     System.out.println("Shopping List succesfully added.");
	 }
	 
	 public static void ajouterArticleListe(int quantite)
	 {
		 Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	     Transaction tx = null;
	     
	     try {
	    	 tx=session.beginTransaction();
	    	 LinkListeProduit linkListProduit = new LinkListeProduit();
	    	 linkListProduit.setQuantite(quantite);
	    	 
	     }catch (Exception e) {
	            if (tx != null) tx.rollback();
	            throw e;
	        } finally {
	            session.close();
	        }
	 }

		
}
		
		
	 



