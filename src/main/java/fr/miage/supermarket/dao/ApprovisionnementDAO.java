package fr.miage.supermarket.dao;

import fr.miage.supermarket.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

/**
 * DAO pour la gestion des stocks d'approvisionnements.
 * Cette classe fournit des méthodes pour accéder aux données des approvisionnements
 * dans la base de données.
 * 
 * @see HibernateUtil
 * @see SessionFactory
 * @see Session
 * @see Transaction
 * 
 * @author AlexP
 */
public class ApprovisionnementDAO {

    private final SessionFactory sessionFactory;
    
    /**
     * Constructeur de la classe ApprovisionnementDAO.
     * Initialise la sessionFactory à partir de HibernateUtil.
     */
    public ApprovisionnementDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
        
        Transaction transact = session.getTransaction();
        if(transact.isActive()) {
        	session.beginTransaction();
        }
    }

    /**
     * Récupère tous les approvisionnements à partir d'aujourd'hui.
     * 
     * @return une liste d'objets contenant les informations des approvisionnements
     */
    public List<Object[]> getAllApprovisionnements() {
        List<Object[]> approvisionnements;
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT a.produit.ean, a.produit.libelle, a.dateArriveeStock, a.magasin.nom, SUM(a.quantiteCommandee) " +
                         "FROM Approvisionnement a " +
                         "WHERE a.dateArriveeStock >= :today " +
                         "GROUP BY a.produit.ean, a.produit.libelle, a.dateArriveeStock, a.magasin.id " +
                         "ORDER BY a.dateArriveeStock";
            Date today = new Date();
            approvisionnements = session.createQuery(hql, Object[].class)
                                        .setParameter("today", today)
                                        .list();
        }
        return approvisionnements;
    }
}
