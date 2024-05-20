package fr.miage.supermarket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * Classe de gestion des données pour les produits
 * @author EricB
 */
public class ProduitDAO {
	
	private SessionFactory sessionFactory;
    
    public ProduitDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }
	
	/**
	 * Enregistre une liste de produits en base
	 * @param produitsToSave les produits à enregistrer
	 * @author EricB
	 */
	public void registerProduits(List<Produit> produitsToSave) {		
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		for(Produit prd: produitsToSave) {
			session.merge(prd);
		}
		session.getTransaction().commit();
		
		session.close();
	}
	/**
	 * Renvoit l'entièreté des produits présents en base
	 * @return la liste des produits
	 * @author EricB
	 */
	public List<Produit> getAllProduits() {
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		
		try {
			Query<Produit> query = session.createQuery("FROM Produit", Produit.class);
			List<Produit> produits = query.getResultList();
			
			session.getTransaction().commit();
			
			return produits;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * Retourne un {@link Produit} en fonction de son EAN
	 * @param ean l'ean du produit à récupérer
	 * @return le {@link Produit} associé à l'ean
	 * @author EricB
	 */
	public Produit getProduitByEan(String ean) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			Produit produit = session.get(Produit.class, ean);
			session.getTransaction().commit();
			return produit;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	/**
	 * Retourne une liste de {@link Produit} détenant le libellé passé en paramètre dans leur libellé respectif.
	 * @param libelle le libelle des produits à rechercher
	 * @return la liste de {@link Produit}
	 * @author EricB
	 */
	public List<Produit> getProduitsByLibelle(String libelle) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			// Concat nécessaire pour bonne prise en compte de tout les caractères
			Query query = session.createQuery("FROM Produit p "
	                + "WHERE p.libelle LIKE CONCAT('%',?1,'%')", Produit.class);
	        query.setParameter(1, libelle);
	        List<Produit> produits = query.getResultList();
			return produits;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public void save(Produit produit) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(produit);
            transaction.commit();
        }
    }
}
