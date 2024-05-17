package fr.miage.supermarket.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
	 public static List<ShoppingList> getShoppingLists(int userId) throws Exception {
	        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	        Transaction tx = null;
	        List<ShoppingList> shoppingLists = new ArrayList<>();

	        try {
	            tx = session.beginTransaction();
	            shoppingLists = session.createQuery("from ShoppingList sl where sl.utilisateur.id = :userId", ShoppingList.class)
                        //TO-DO :: remplacer par l'ID de l'User CONNECTÉ QUAND authentifaction sera faite
	            		// -- Code à remplacer
	            		.setParameter("userId", userId)
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
	  * @author Pauline
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
	    	 session.persist(listeCourse);
	    	 
	    	 tx.commit();
	     } catch (Exception e) {
	            if (tx != null) tx.rollback();
	            throw e;
	        } finally {
	            session.close();
	        }
	     System.out.println("Shopping List succesfully added.");
	 }	 
	 /**
	  * Methode pour supprimer une liste de course
	  * @param listeId, identifiant de la liste de course a supprimer
	  * @author Pauline
	  */
	 public static void supprimerListe(int listeId )
	 {
		 Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	     Transaction tx = null;
	     
	     try {
	    	 tx=session.beginTransaction();
	    	 ShoppingList liste = session.get(ShoppingList.class, listeId);
	    	 if (liste != null) {
	             session.remove(liste);
	         }
	    	 tx.commit();
	     }catch (Exception e) {
	            if (tx != null) tx.rollback();
	            throw e;
	        } finally {
	            session.close();
	        }
	 }
	 
	 public static List<LinkListeProduit> getLinkListProduit(int listeId) throws Exception {
	        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	        Transaction tx = null;
	        List<LinkListeProduit> linkListeProduits = new ArrayList<>();

	        try {
	            tx = session.beginTransaction();
	            Query<LinkListeProduit> query = session.createQuery(
	                    "from LinkListeProduit pl where pl.shoppingList.id = :listeId", LinkListeProduit.class);
	                query.setParameter("listeId", listeId);
	                linkListeProduits = query.list();
	                tx.commit();
	        } catch (Exception e) {
	            if (tx != null) tx.rollback();
	            throw e;
	        } finally {
	            session.close();
	        }
	     // AFFICHAGE RESULTAT CONSOLE
	        for (LinkListeProduit link : linkListeProduits) {
	            System.out.println("Produit EAN: " + link.getProduit().getEan() + 
	                               ", Quantité: " + link.getQuantite());
	        }
	    // FIN 
	        return linkListeProduits;
	    }
	 
	 /**
	  * Methode pour ajouté une quantité de produit à une liste - EN COURS-
	  * @param quantite
	  */
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
		
		
	 



