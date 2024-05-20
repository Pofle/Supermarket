package fr.miage.supermarket.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Rayon;
import fr.miage.supermarket.utils.HibernateUtil;

public class RayonDAO {

	private SessionFactory sessionFactory;

    public RayonDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }
    
    public Rayon findByLibelle(String libelle) {
        try (Session session = sessionFactory.openSession()) {
            Query<Rayon> query = session.createQuery("FROM Rayon WHERE libelle = :libelle", Rayon.class);
            query.setParameter("libelle", libelle);
            return query.uniqueResult();
        }
    }

    public void save(Rayon rayon) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(rayon);
            transaction.commit();
        }
    }
}
