package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Classe Data Access Object (DAO) pour accéder aux informations de stock dans la base de données.
 * Cette classe utilise Hibernate pour interagir avec la base de données.
 * @author : AlexP
 */
public class StockDAO {
	
    /**
     * Récupère les informations de stock pour une date donnée.
     * Cette méthode utilise une requête HQL pour sélectionner les informations des produits,
     * les quantités en stock, les noms des magasins, et la date de stock selectionnée en paramètre.
     * @param date la date pour laquelle récupérer les informations de stock
     * @return une liste d'objets, où chaque objet est un tableau contenant les informations
     *         sur un produit, la quantité en stock, le nom du magasin et la date de stock
     */
	public List<Object[]> getProduitsStockDate(Date date) {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
        
        session.beginTransaction();
        
        try {
            Query<Object[]> query = session.createQuery(
                "SELECT p, COALESCE(SUM(lps.quantite), 0), m.nom, :date " +
                "FROM Produit p " +
                "LEFT JOIN p.linkProduitStocks lps " +
                "LEFT JOIN lps.stock s " +
                "LEFT JOIN lps.magasin m " +
                "WHERE s.dateStock = :date OR s.dateStock IS NULL " +
                "GROUP BY p, m.nom " +
                "ORDER BY COALESCE(SUM(lps.quantite), 0)", Object[].class
            );
            query.setParameter("date", date);
            List<Object[]> results = query.getResultList();
            
            session.getTransaction().commit();
            
            System.out.println("Number of results: " + results.size());
            for (Object[] result : results) {
                System.out.println(Arrays.toString(result));
            }
            
            return results;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

     /**
     * Récupère les informations de stock pour une série de dates données.
     * @param serieDates la liste des dates pour lesquelles récupérer les informations de stock
     * @return une liste d'objets, où chaque objet est un tableau contenant les informations
     *         sur un produit, la quantité en stock, le nom du magasin et la date de stock
     *         pour chaque date dans la série
     */
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

	public void retirerProduitCommandesStock (String ean, int id_magasin, int quantite_retiree) {
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
        
        session.beginTransaction();
	    try {
	    	Query query = session.createQuery("SELECT quantite FROM Link_Produit_Stock WHERE produit.ean = :ean AND magasin.id = :id_magasin");
	    	query.setParameter("ean", ean);
	    	query.setParameter("id_magasin", id_magasin);
	    	int qte_actuelle = (int) query.list().get(0);
	    	int qte_new = qte_actuelle - quantite_retiree;
	    	query = session.createQuery("UPDATE Link_Produit_Stock SET quantite = :qte_new WHERE produit.ean = :ean AND magasin.id = :id_magasin");
			query.setParameter("qte_new", qte_new);
			query.setParameter("ean", ean);
	    	query.setParameter("id_magasin", id_magasin);
			int result = query.executeUpdate();
	    	session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
}
