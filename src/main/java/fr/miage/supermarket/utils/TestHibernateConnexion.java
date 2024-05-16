package fr.miage.supermarket.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TestHibernateConnexion {

    public static void main(String[] args) {
        // Obtenir la SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        
        // Ouvrir une session
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            // Test simple : récupérer la date/heure du serveur MySQL
            String sql = "SELECT NOW()";
            String result = (String) session.createNativeQuery(sql).getSingleResult();
            
            // Afficher le résultat
            System.out.println("La connexion à la base de données est réussie : " + result);
            
            // Commit de la transaction
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
        
        // Fermer la SessionFactory
        sessionFactory.close();
    }
}
