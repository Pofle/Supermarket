package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Classe permettant de gerer les services lies aux produits et listes
 * @author Pauline
 */
public class LinkListeProduitDAO {
	/**
	 * Methode pour recuperer les produits link à une liste
	 * @return liste de l'ensemble des produits contenus dans les listes
	 * @throws Exception si une erreur survient
	 * @author Pauline
	 */
	public static List<LinkListeProduit> getAllLinkListProduit() throws Exception {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction tx = null;
        List<LinkListeProduit> linkListeProduits = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            Query<LinkListeProduit> query = session.createQuery(
                    "from LinkListeProduit", LinkListeProduit.class);
                linkListeProduits = query.list();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return linkListeProduits;
    }
	
	/**
	 * Methode pour récupérer les produits contenu dans une liste par l'id de la liste de course
	 * @param listeId
	 * @return
	 * @throws Exception
	 * @author Pauline
	 */
	public static List<LinkListeProduit> getLinkListeProduitByListeId(int listeId) throws Exception {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction tx = null;
        List<LinkListeProduit> linkListeProduits = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            Query<LinkListeProduit> query = session.createQuery(
                "from LinkListeProduit where shoppingList.id = :listId", LinkListeProduit.class);
            query.setParameter("listId", listeId);
            linkListeProduits = query.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return linkListeProduits;
    }
	
	public static void updateProduitsListe(int listeId, String ean, int newQuantity) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();
	        Query<LinkListeProduit> query = session.createQuery(
	                "from LinkListeProduit where shoppingList.id = :listeId and produit.ean = :ean", LinkListeProduit.class);
	        query.setParameter("listeId", listeId);
	        query.setParameter("ean", ean);
	        LinkListeProduit linkListeProduit = query.uniqueResult();
	        if (linkListeProduit != null) {
	            linkListeProduit.setQuantite(newQuantity);
	            session.update(linkListeProduit);
	        }
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	
	public static void supprimerProduit(String eanProduit, int listeId) {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Recherche du lien entre le produit et la liste à supprimer
            Query<LinkListeProduit> query = session.createQuery("FROM LinkListeProduit WHERE produit.ean = :ean AND shoppingList.id = :listeId", LinkListeProduit.class);
            query.setParameter("ean", eanProduit);
            query.setParameter("listeId", listeId);
            LinkListeProduit link = query.uniqueResult();

            if (link != null) {
                // Suppression du lien entre le produit et la liste
                session.delete(link);
            } else {
                System.out.println("Le lien entre le produit avec l'EAN " + eanProduit + " et la liste avec l'ID " + listeId + " n'a pas été trouvé.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

	
 /**
  * Methode pour ajouter une quantité de produit à une liste - EN COURS-
  * @param quantite de produit a ajouter dans la liste
  * @author Pauline
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
