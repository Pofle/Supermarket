package fr.miage.supermarket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.utils.HibernateUtil;

public class MagasinDAO {

    public static List<Magasin> getAllMagasins() {
        List<Magasin> magasins = null;
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;

        try {
            sessionFactory = HibernateUtil.getSessionAnnotationFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Magasin> query = session.createQuery("from Magasin", Magasin.class);
            magasins = query.list();

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

        return magasins;
    }
}
