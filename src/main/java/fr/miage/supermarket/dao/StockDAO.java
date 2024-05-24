package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Link_Produit_Stock;
import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.models.Produit;
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
	
	private SessionFactory sessionFactory;
	
	public StockDAO() {
		this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
	}
	
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

    /**
     * Retire une certaine quantité d'un produit du stock associé à un magasin pour une date spécifique et celles supérieures à cette date.
     * Cette méthode met à jour la quantité disponible du produit dans le stock du magasin.
     *
     * @param ean l'EAN du produit à retirer du stock
     * @param idMagasin l'identifiant du magasin où le produit doit être retiré du stock
     * @param quantiteRetiree la quantité du produit à retirer du stock
     * @param date la date à partir de laquelle les quantités doivent être mises à jour
     */
    public void retirerProduitCommandesStock(String ean, int idMagasin, int quantiteRetiree, Date date) {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Query<Link_Produit_Stock> query = session.createQuery(
                "FROM Link_Produit_Stock lps WHERE lps.produit.ean = :ean AND lps.magasin.id = :idMagasin AND lps.stock.dateStock >= :date",
                Link_Produit_Stock.class);
            query.setParameter("ean", ean);
            query.setParameter("idMagasin", idMagasin);
            query.setParameter("date", date);

            for (Link_Produit_Stock lps : query.getResultList()) {
                int nouvelleQuantite = lps.getQuantite() - quantiteRetiree;
                lps.setQuantite(nouvelleQuantite < 0 ? 0 : nouvelleQuantite);
                session.merge(lps);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }
	
	/**
     * Récupère les produits de la liste qui ne sont pas en stock pour un magasin spécifique à une date donnée.
     *
     * @param date la date pour laquelle vérifier les stocks
     * @param magasin le magasin pour lequel vérifier les stocks
     * @param produits la liste de produits à vérifier
     * @return une liste de produits qui ne sont pas en stock
     */
	public List<Produit> getProduitsNonEnStockPourCommande(Date date, Magasin magasin, Commande commande) {
        Session session = sessionFactory.openSession();
        List<Produit> result = null;
        try {
            String hql = "SELECT lcp.produit " +
                         "FROM LinkCommandeProduit lcp " +
                         "WHERE lcp.commande = :commande " +
                         "AND lcp.produit NOT IN (" +
                         "    SELECT lps.produit " +
                         "    FROM Link_Produit_Stock lps " +
                         "    WHERE lps.magasin = :magasin " +
                         "    AND lps.stock.dateStock = :date " +
                         "    AND lps.quantite >= lcp.quantite" +
                         ")";
            Query<Produit> query = session.createQuery(hql, Produit.class);
            query.setParameter("commande", commande);
            query.setParameter("magasin", magasin);
            query.setParameter("date", date);
            result = query.getResultList();
        } finally {
            session.close();
        }
        return result;
    }
}
