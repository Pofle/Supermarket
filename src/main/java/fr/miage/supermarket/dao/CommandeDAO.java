package fr.miage.supermarket.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.utils.HibernateUtil;

public class CommandeDAO {
    public void save(Commande commande) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionAnnotationFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(commande);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
