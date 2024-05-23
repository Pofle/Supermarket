package fr.miage.supermarket.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Rayon;
import fr.miage.supermarket.utils.HibernateUtil;
import jakarta.persistence.TypedQuery;

/**
 * Cette classe fournit des méthodes pour interagir avec la base de données concernant les rayons.
 * @author EricB
 */
public class RayonDAO {

	private SessionFactory sessionFactory;

    public RayonDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }
    
    /**
     * Récupère un rayon à partir de son libellé.
     *
     * @param libelle Le libellé du rayon à récupérer.
     * @return Le rayon correspondant au libellé spécifié, ou null s'il n'existe pas.
     * @author EricB
     */
    public Rayon findByLibelle(String libelle) {
        try (Session session = sessionFactory.openSession()) {
            Query<Rayon> query = session.createQuery("FROM Rayon WHERE libelle = :libelle", Rayon.class);
            query.setParameter("libelle", libelle);
            return query.uniqueResult();
        }
    }

    /**
     * Enregistre ou met à jour un rayon dans la base de données.
     *
     * @param rayon Le rayon à enregistrer ou mettre à jour.
     * @author EricB
     */
    public void save(Rayon rayon) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(rayon);
            transaction.commit();
        }
    }
    
    /**
     * Récupère tous les rayons de la base de données.
     *
     * @return Une liste contenant tous les rayons, ou null en cas d'erreur.
     * @author EricB
     */
    public List<Rayon> getAllRayons() {
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		
		try {
			Query<Rayon> query = session.createQuery("FROM Rayon", Rayon.class);
			List<Rayon> categories = query.getResultList();
			
			session.getTransaction().commit();
			
			return categories;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
    
    /**
     * Récupère un rayon à partir de son identifiant, en chargeant également ses catégories associées.
     *
     * @param idRayon L'identifiant du rayon à récupérer.
     * @return Le rayon correspondant à l'identifiant spécifié, ou null s'il n'existe pas.
     * @author EricB
     */
    public Rayon findRayonById(int idRayon) {
    	try (Session session = sessionFactory.openSession()) {
    		Query<Rayon> query = session.createQuery("SELECT r FROM Rayon r LEFT JOIN FETCH r.categories WHERE r.id = :idrayon", Rayon.class);
			query.setParameter("idrayon", idRayon);
            return query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
