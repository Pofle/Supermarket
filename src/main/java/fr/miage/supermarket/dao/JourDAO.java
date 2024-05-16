package fr.miage.supermarket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Jour;
import fr.miage.supermarket.utils.HibernateUtil;

public class JourDAO {

    public static List<Jour> getAllJours() {
        List<Jour> jours = null;
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;

        try {
            sessionFactory = HibernateUtil.getSessionAnnotationFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Jour> query = session.createQuery("from Jour", Jour.class);
            jours = query.list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return jours;
    }
}
