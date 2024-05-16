import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Creneau;
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

     // Création des créneaux si nécessaire
        List<Creneau> creneaux = getCreneaux(sessionFactory);
        if (creneaux.isEmpty()) {
            creneaux = genererCreneaux();
            enregistrerCreneaux(creneaux, sessionFactory);
        }

        // Enregistrement du magasin et des créneaux en base de données
        // Vérifier si l'ID du magasin existe déjà
        if (magasinExiste(magasin.getId(), sessionFactory)) {
            System.out.println("Magasin avec l'ID " + magasin.getId() + " existe déjà !");
        } else {
            // Enregistrement du magasin en base de données
            enregistrerMagasin(magasin, sessionFactory);
        }

        // Création de la commande avec un créneau
        Commande commande = new Commande();
        commande.setId(1);
        commande.setMagasin(magasin);
        commande.setCreneau(creneaux.get(0)); // Ajout du premier créneau à la commande

     // Vérifier si l'ID de la commande existe déjà
        if (commandeExiste(commande.getId(), sessionFactory)) {
            System.out.println("Commande avec l'ID " + commande.getId() + " existe déjà !");
        } else {
            // Vérifier la disponibilité du créneau
            Creneau creneauSelectionne = commande.getCreneau();
            if (creneauSelectionne != null && creneauSelectionne.getNombreMaxCommandes() > 0) {
                // Enregistrement de la commande en base de données
                enregistrerCommande(commande);

                // Mise à jour de la disponibilité du créneau
                creneauSelectionne.setNombreMaxCommandes(creneauSelectionne.getNombreMaxCommandes() - 1);
                mettreAJourCreneau(creneauSelectionne);
            } else {
                System.out.println("Le créneau sélectionné n'est pas disponible !");
            }
        }

        // Fermeture de la SessionFactory à la fin du programme
        sessionFactory.close();
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

    // méthode permettant l'enregistrement des créneaux
    private static void enregistrerCreneaux(List<Creneau> creneaux, SessionFactory sessionFactory) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            for (Creneau creneau : creneaux) {
                session.save(creneau);
            }
            transaction.commit();
            System.out.println("Créneaux enregistrés avec succès !");
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

    // méthode permettant de générer les créneaux de retrait de commande selon la disponibilité
    private static List<Creneau> genererCreneaux() throws ParseException {
        List<Creneau> creneaux = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date heureDebut = sdf.parse("09:00:00");

        for (int i = 0; i < 40; i++) { // Création de 32 créneaux de 15 minutes
            Creneau creneau = new Creneau();
            creneau.setHeureDebut(heureDebut);
            creneau.setHeureFin(new Date(heureDebut.getTime() + 15 * 60 * 1000));
            creneau.setNombreMaxCommandes(4);
            creneaux.add(creneau);
            heureDebut = new Date(heureDebut.getTime() + 15 * 60 * 1000); // Passage au créneau suivant
        }

        return creneaux;
    }
    
    private static List<Creneau> getCreneaux(SessionFactory sessionFactory) {
        List<Creneau> creneaux = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            creneaux = session.createQuery("FROM Creneau", Creneau.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return creneaux;
    }
    
    //méthode permettant de mettre à jour les disponibilité des créneaux
    private static void mettreAJourCreneau(Creneau creneau) {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(creneau);
            transaction.commit();
            System.out.println("Créneau mis à jour avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}


