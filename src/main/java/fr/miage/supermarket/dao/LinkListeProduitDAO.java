package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ShoppingList;
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
	
	/**
	 * Methode pour mettre à jour les quantités de produits d'une liste
	 * @param listeId, l'id de la liste à mettre à jour
	 * @param ean, ean des produits à mettre à jour
	 * @param newQuantity, la quanité à mettre à jour
	 * @author Pauline
	 */
	public static void updateProduitsListe(int listeId, String ean, int newQuantity) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();
	        Query<LinkListeProduit> query = session.createQuery(
	                "FROM LinkListeProduit WHERE shoppingList.id = :listeId and produit.ean = :ean", LinkListeProduit.class);
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
	
	/**
	 * Methode pour supprimer un produit d'une liste de course
	 * @param eanProduit, du produit à supprimer
	 * @param listeId, de la liste où il doit être supprimé
	 * @author Pauline
	 */
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
                session.remove(link);
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
	 * Service pour ajouter un produit à une liste de course
	 * @param listeId, liste dans laquelle est ajouté le produit
	 * @param ean, ean du pr, quantité de produit à ajouter dans la liste
	 * @author Pauline
	 */
	public static void ajouterProduitListe(int listeId, String ean, int quantite) {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query<LinkListeProduit> query = session.createQuery(
                "FROM LinkListeProduit WHERE shoppingList.id = :listeId and produit.ean = :ean", LinkListeProduit.class);
            query.setParameter("listeId", listeId);
            query.setParameter("ean", ean);
            LinkListeProduit linkListeProduit = query.uniqueResult();
            

            if (linkListeProduit != null) {
                // Si le produit existe déjà dans la liste, mettre à jour la quantité
                linkListeProduit.setQuantite(linkListeProduit.getQuantite() + quantite);
                session.update(linkListeProduit);
            } else {
                // Sinon, ajouter un nouveau produit à la liste
                ShoppingList shoppingList = session.get(ShoppingList.class, listeId);
                Produit produit = session.get(Produit.class, ean);
                linkListeProduit = new LinkListeProduit();
                linkListeProduit.setShoppingList(shoppingList);
                linkListeProduit.setProduit(produit);
                linkListeProduit.setQuantite(quantite);
                session.save(linkListeProduit);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
