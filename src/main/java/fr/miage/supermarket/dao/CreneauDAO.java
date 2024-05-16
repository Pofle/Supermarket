package fr.miage.supermarket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Creneau;
import fr.miage.supermarket.utils.HibernateUtil;

public class CreneauDAO {

    public static List<Creneau> getAllCreneaux() {
        List<Creneau> creneaux = null;
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;

        try {
            sessionFactory = HibernateUtil.getSessionAnnotationFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Creneau> query = session.createQuery("from Creneau", Creneau.class);
            creneaux = query.list();

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

        return creneaux;
    }
}
