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
 * DAO pour la gestion des stocks.
 * Cette classe fournit les méthodes pour accéder aux données de stock dans la base de données.
 * 
 * @see HibernateUtil
 * @see SessionFactory
 * @see Session
 * @see Query
 * 
 * @author AlexP
 */
public class StockDAO {
	
    /**
     * Récupère les informations sur les produits en stock pour une date donnée.
     * 
     * @param date la date pour laquelle récupérer les informations de stock
     * @return une liste d'objets contenant les informations sur les produits en stock pour la date spécifiée
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
            
            System.out.println("Nombre de résultats : " + results.size());
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
     * Récupère les informations sur les produits en stock pour une série de dates.
     * 
     * @param serieDates la série de dates pour lesquelles récupérer les informations de stock
     * @return une liste d'objets contenant les informations sur les produits en stock pour la série de dates spécifiée
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
}
