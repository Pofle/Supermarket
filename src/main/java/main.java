import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.models.Produit;
import org.hibernate.SessionFactory;

import java.util.List;

import org.hibernate.Session;

import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.HibernateUtil;

public class main {

    public static void main(String[] args) throws ParseException {

        Produit article = new Produit();
        article.setEan("oidfjz");
        // Récupération de la session
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();

        // session.beginTransaction();
        // session.persist(article);
        // session.getTransaction().commit();
        // System.out.println("Article ID="+article.getEan());

        // Ferme la session
        session.close();

        // Création d'un magasin
        Magasin magasin = new Magasin();
        magasin.setId(31);
        magasin.setNom("SuperMarket Sud");
        magasin.setAdresse("Toulouse");
        
        Magasin magasin1 = new Magasin();
        magasin1.setId(69);
        magasin1.setNom("SuperMarket Ouest");
        magasin1.setAdresse("Lyon");
        
        Magasin magasin2 = new Magasin();
        magasin2.setId(70);
        magasin2.setNom("SuperMarket Centre");
        magasin2.setAdresse("Paris");


        // Enregistrement du magasin et des créneaux en base de données
        // Vérifier si l'ID du magasin existe déjà
        if (magasinExiste(magasin.getId(), sessionFactory)) {
            System.out.println("Magasin avec l'ID " + magasin.getId() + " existe déjà !");
        } else {
            // Enregistrement du magasin en base de données
            enregistrerMagasin(magasin, sessionFactory);
        }

        
    }

    // méthode permettant l'enregistrement d'un magasin
    private static void enregistrerMagasin(Magasin magasin, SessionFactory sessionFactory) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(magasin);
            transaction.commit();
            System.out.println("Magasin enregistré avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    // méthode permettant l'enregistrement des commandes
    private static void enregistrerCommande(Commande commande) {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(commande);
            transaction.commit();
            System.out.println("Commande enregistrée avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // méthode permettant de vérifier si la commande enregistrée existe déjà
    private static boolean commandeExiste(int idCommande, SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Commande commande = session.get(Commande.class, idCommande);
            session.getTransaction().commit();
            return commande != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    // méthode permetant de vérifier si le magasin existe déjà
    private static boolean magasinExiste(int idMagasin, SessionFactory sessionFactory) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Magasin magasin = session.get(Magasin.class, idMagasin);
            session.getTransaction().commit();
            return magasin != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
 
}


