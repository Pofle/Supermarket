package fr.miage.supermarket.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.HibernateUtil;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;

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

	public List<Produit> getProduitsByFilters(String libelle, String categorie, String rayon) {
        String query = "SELECT p FROM Produit p WHERE 1=1";
        Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
        Map<String, Object> params = new HashMap<>();

        if (libelle != null && !libelle.isEmpty()) {
            query += " AND p.libelle LIKE CONCAT('%',:libelle,'%')";
            params.put("libelle", "%" + libelle + "%");
        }
        if (categorie != null && !categorie.isEmpty()) {
            query += " AND p.categorie.libelle = :categorie";
            params.put("categorie", categorie);
        }
        if (rayon != null && !rayon.isEmpty()) {
            query += " AND p.categorie.rayon.libelle = :rayon";
            params.put("rayon", rayon);
        }

        TypedQuery<Produit> typedQuery = session.createQuery(query, Produit.class);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            typedQuery.setParameter(param.getKey(), param.getValue());
        }

        return typedQuery.getResultList();
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
            session.merge(produit);
            transaction.commit();
        }
    }
	
	public int getQuantiteCommandee(Produit produit) {
		Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT SUM(lp.quantite) FROM LinkCommandeProduit lp WHERE lp.produit = :produit", Long.class);
        query.setParameter("produit", produit);
        Long sum = (Long) query.getSingleResult();
        return sum != null ? sum.intValue() : 0;
    }
	
	public List<Produit> getProduitsParIdCommande(int commandeId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			TypedQuery<Produit> query = session.createQuery(
					"SELECT p FROM Produit p JOIN LinkCommandeProduit lcp ON p.ean = lcp.produit.ean WHERE lcp.commande.id_commande = :commandeId", 
					Produit.class);
			query.setParameter("commandeId", commandeId);
			return query.getResultList();
			
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
			finally {
			session.close();
		}
    }
	
}
