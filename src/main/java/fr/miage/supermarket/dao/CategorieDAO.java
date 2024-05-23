package fr.miage.supermarket.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Categorie;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Cette classe implémente les opérations d'accès aux données pour l'entité Categorie.
 * @author EricB
 */
public class CategorieDAO {
	
	private SessionFactory sessionFactory;

    public CategorieDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }

    /**
     * Recherche une catégorie par son libellé.
     *
     * @param libelle Le libellé de la catégorie à rechercher.
     * @return La catégorie correspondante, ou null si aucune catégorie correspondante n'est trouvée.
     * @author EricB
     */
    public Categorie findByLibelle(String libelle) {
        try (Session session = sessionFactory.openSession()) {
            Query<Categorie> query = session.createQuery("FROM Categorie WHERE libelle = :libelle", Categorie.class);
            query.setParameter("libelle", libelle);
            return query.uniqueResult();
        }
    }

    /**
     * Enregistre une catégorie dans la base de données.
     *
     * @param categorie La catégorie à enregistrer.
     * @author EricB
     */
    public void save(Categorie categorie) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(categorie);
            transaction.commit();
        }
    }
}
