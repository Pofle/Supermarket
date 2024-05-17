/**
 * 
 */
package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Classe permettant de gerer les services lies 
 */
public class LinkListeProduitDAO {
	/**
	 * Methode pour recuperer les produits contenus dans une liste
	 * @param listeId, l'id de la liste à récupérer
	 * @return
	 * @throws Exception
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
                //tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return linkListeProduits;
    }
	
 /**
  * Methode pour ajouter une quantité de produit à une liste - EN COURS-
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
