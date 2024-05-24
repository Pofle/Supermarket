package fr.miage.supermarket.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Magasin;
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
    
    public Produit find(String ean) {
    	try (Session session = sessionFactory.openSession()) {
    		return session.find(Produit.class, ean);
    	}
    }
	
	/**
	 * Enregistre une liste de produits en base
	 * @param produitsToSave les produits à enregistrer
	 * @author EricB
	 */
	public void saveProduits(List<Produit> produitsToSave) {		
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
	        Query<Produit> query = session.createQuery("SELECT DISTINCT p FROM Produit p LEFT JOIN FETCH p.promotions", Produit.class);
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
	 * Récupère une liste de produits en fonction des filtres spécifiés.
	 *
	 * @param libelle Le libellé partiel ou complet du produit à rechercher.
	 * @param categorie Le libellé de la catégorie à laquelle appartient le produit.
	 * @param rayon Le libellé du rayon auquel appartient la catégorie du produit.
	 * @return Une liste de produits correspondant aux filtres spécifiés.
	 * @author EricB
	 */
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
	 * Enregistre ou met à jour un produit dans la base de données.
	 *
	 * @param produit Le produit à enregistrer ou mettre à jour.
	 * @author EricB
	 */
	public void save(Produit produit) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(produit);
            transaction.commit();
        }
    }
	
	/**
	 * Récupère la quantité totale commandée d'un produit spécifique.
	 *
	 * @param produit Le produit pour lequel récupérer la quantité commandée.
	 * @return La quantité totale commandée du produit, ou 0 s'il n'y a pas de commande pour ce produit.
	 */
	public int getQuantiteCommandee(Produit produit) {
		Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT SUM(lp.quantite) FROM LinkCommandeProduit lp WHERE lp.produit = :produit", Long.class);
        query.setParameter("produit", produit);
        Long sum = (Long) query.getSingleResult();
        return sum != null ? sum.intValue() : 0;
    }
	
	 /**
	 * Récupère la liste des produits associés à une commande spécifique.
	 *
	 * @param commandeId L'identifiant de la commande pour laquelle récupérer les produits associés.
	 * @return La liste des produits associés à la commande spécifiée, ou null en cas d'erreur.
	 */
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
	
	/**
	 * Récupère une liste d'objets contenant des informations sur les produits avec les promotions et le nombre d'achats pour une catégorie de produit donnée.
	 *
	 * @param categorieId L'identifiant de la catégorie pour laquelle récupérer les informations.
	 * @return Une liste d'objets où chaque objet contient les informations suivantes :
	 *         - 0 - Produit : L'objet Produit.
	 *         - 1 - Pourcentage de la promotion appliquée sur le produit.
	 *         - 2 - Le nombre total d'achats du produit.
	 *         Les résultats sont triés par ordre décroissant du nombre total d'achats.
	 * @author EricB
	 */
	public List<Object[]> findProduitsWithPromotionsAndPurchaseCountByCategorieId(int categorieId) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT p, pr.pourcentage, SUM(COALESCE(lcp.quantite, 0)) " +
                         "FROM Produit p " +
                         "LEFT JOIN p.promotions pr " +
                         "LEFT JOIN p.linkProduitsCommande lcp " +
                         "WHERE p.categorie.id = :categorieId " +
                         "AND (pr.dateDebut IS NULL OR pr.dateDebut <= CURRENT_DATE) " +
                         "AND (pr.dateFin IS NULL OR pr.dateFin >= CURRENT_DATE) " +
                         "GROUP BY p, pr.pourcentage"
                         + " ORDER BY SUM(COALESCE(lcp.quantite, 0)) DESC";
            Query<Object[]> query = session.createQuery(hql, Object[].class)
                                         .setParameter("categorieId", categorieId);
            return query.list();
        } finally {
            session.close();
        }
    }
	
	/**
	 * Récupère tous les produits de remplacement en stock de la même catégorie que le produit passé en paramètre,
	 * pour un magasin donné et une date donnée.
	 *
	 * @param produit Le produit pour lequel on cherche des produits de remplacement en stock.
	 * @param magasin Le magasin pour lequel on vérifie le stock des produits de remplacement.
	 * @param date    La date pour laquelle on vérifie le stock des produits de remplacement.
	 * @return Une liste des produits de remplacement en stock de la même catégorie que le produit spécifié,
	 *         pour le magasin spécifié et la date spécifiée.
	 */
	public List<Produit> getProduitsRemplacementEnStock(Produit produit, Magasin magasin, Date date) {
	    Session session = sessionFactory.openSession();
	    List<Produit> result = null;
	    try {
	        String hql = "SELECT p " +
	                     "FROM Produit p " +
	                     "WHERE p.categorie = :categorie " +
	                     "AND p IN (" +
	                     "    SELECT lps.produit " +
	                     "    FROM Link_Produit_Stock lps " +
	                     "    WHERE lps.magasin = :magasin " +
	                     "    AND lps.stock.dateStock = :date " +
	                     "    AND lps.quantite > 0" +
	                     ")";
	        Query<Produit> query = session.createQuery(hql, Produit.class);
	        query.setParameter("categorie", produit.getCategorie());
	        query.setParameter("magasin", magasin);
	        query.setParameter("date", date);
	        result = query.getResultList();
	    } finally {
	        session.close();
	    }
	    return result;
	}
}
