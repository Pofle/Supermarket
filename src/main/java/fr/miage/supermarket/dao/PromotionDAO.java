package fr.miage.supermarket.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.query.Query;

import fr.miage.supermarket.utils.HibernateUtil;

import org.hibernate.SessionFactory;

/**
 * Objet d'accès aux données de promotion
 * @author EricB
 */
public class PromotionDAO {
	
	private SessionFactory sessionFactory;
    
    public PromotionDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }
    
    /**
     * Retourne le taux de promotion relatif au produit dont l'ean est passé en paramètre
     * 
     * @param produitEan l'ean du produit dont récupérer la promotion en cours
     * @return le taux de promotion sous la forme d'un flottant
     * @author EricB
     */
    public Float getPromotionPourProduit(String produitEan) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT p.pourcentage FROM Promotion p JOIN p.produits prod " +
                         "WHERE prod.ean = :ean AND :currentDate BETWEEN p.dateDebut AND p.dateFin";
            Query<Float> query = session.createQuery(hql, Float.class);
            query.setParameter("ean", produitEan);
            query.setParameter("currentDate", new Date());
            List<Float> result = query.getResultList();
            if (!result.isEmpty()) {
                return result.get(0);
            } else {
                return null;
            }
        } finally {
            session.close();
        }
    }
    
    /**
     * Retourne, sous la forme d'une map, la promotion en cours pour chacun des produits dont l'EAN est passé en paramètre
     * 
     * @param eanList la liste des EAN des produits dont récupérer la promotion en cours
     * @return la map avec l'EAN en clef et la promotion en valeur associée à la clef.
     * @author EricB
     */
    public Map<String, Float> getPromotionsPourProduits(List<String> eanList) {
        try (Session session = sessionFactory.openSession()) {
            Query<Object[]> query = session.createQuery(
                "select prod.ean, p.pourcentage from Promotion p join p.produits prod " +
                "where prod.ean in :eanList and p.dateDebut <= :today and p.dateFin >= :today", Object[].class);
            query.setParameter("eanList", eanList);
            query.setParameter("today", new Date());

            return query.getResultList().stream()
                .collect(Collectors.toMap(result -> (String) result[0], result -> (Float) result[1]));
        }
    }
}
