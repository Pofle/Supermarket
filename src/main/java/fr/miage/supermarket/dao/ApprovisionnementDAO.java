package fr.miage.supermarket.dao;

import fr.miage.supermarket.models.Approvisionnement;
import fr.miage.supermarket.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ApprovisionnementDAO {

    private final SessionFactory sessionFactory;
    
    public ApprovisionnementDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
        
        session.beginTransaction();
    }

    public List<Approvisionnement> getAllApprovisionnements() {
        List<Approvisionnement> approvisionnements;
        try (Session session = sessionFactory.openSession()) {
            approvisionnements = session.createQuery("FROM Approvisionnement", Approvisionnement.class).getResultList();
        }
        return approvisionnements;
    }
}
