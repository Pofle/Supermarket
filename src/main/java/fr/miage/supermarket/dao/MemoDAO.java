/**
 * 
 */
package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import fr.miage.supermarket.models.Memo;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * 
 */
public class MemoDAO {
	
	public static List<Memo> getMemos(List<ShoppingList> shoppingLists) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction tx = null;        
        List<Memo> memoList = null;
	
	try {
        tx = session.beginTransaction();
        List<Integer> shoppingListIds = shoppingLists.stream()
                .map(ShoppingList::getId)
                .collect(Collectors.toList());
        	memoList = session.createQuery("SELECT m FROM Memo m JOIN m.shoppingList s WHERE s.id IN :shoppingListIds", Memo.class)
        			.setParameter("shoppingListIds", shoppingListIds)
        			.list();
    } catch (Exception e) {
        if (tx != null) tx.rollback();
        throw e;
    } finally {
        session.close();
    }
    return memoList;
	}
	
	public static List<Memo> getMemoByListId(int listeId) throws Exception{
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction tx = null;        
        List<Memo> memoContent = null;
        
        try {
        	tx = session.beginTransaction();
        	Query<Memo> query = session.createQuery("FROM Memo where shoppingList.id = :listeId", Memo.class);
        	            query.setParameter("listeId", listeId);
        	            memoContent = query.list();       	
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return memoContent;
	}
	
	
	public static void ajouterMemo(String libelle, int listeId) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();
	        Memo memo = new Memo();
	        memo.setLibelle(libelle);

	        ShoppingList shoppingList = session.get(ShoppingList.class, listeId);
	        memo.setShoppingList(shoppingList);

	        session.persist(memo);
	        tx.commit();
	        System.out.println("Mémo ajouté avec succès.");
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        throw e;
	    } finally {
	        session.close();
	    }
	}
	
	public static void supprimerMemo(int memoId, int listeId)
	 {
		 Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		    Transaction tx = null;

		    try {
		        tx = session.beginTransaction();
		        
		        // Suppression du mémo spécifique
		        Memo memo = session.get(Memo.class, memoId);
		        if (memo != null && memo.getShoppingList().getId() == listeId) {
		            session.remove(memo);
		        }
		        
		        tx.commit();
		        System.out.println("Mémo supprimé avec succès.");
		    } catch (Exception e) {
		        if (tx != null) tx.rollback();
		        throw e;
		    } finally {
		        session.close();
		    }
		}
	
	public static List<Produit> rechercherProduitsPourMemos(List<String> memos) {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
            Root<Produit> root = query.from(Produit.class);
            query.select(root);

            List<Predicate> predicates = new ArrayList<>();
            for (String memo : memos) {
                // Créer une condition de recherche pour le libellé et la marque
                Predicate libellePredicate = builder.like(builder.lower(root.get("libelle")), "%" + memo.toLowerCase() + "%");
                Predicate marquePredicate = builder.like(builder.lower(root.get("marque")), "%" + memo.toLowerCase() + "%");
                // Ajouter la condition de recherche combinant libellé et marque avec un OU logique
                predicates.add(builder.or(libellePredicate, marquePredicate));
            }

            // Combiner toutes les conditions avec un OU logique
            Predicate finalPredicate = builder.or(predicates.toArray(new Predicate[0]));
            query.where(finalPredicate);

            // Exécuter la requête et récupérer les produits correspondants
            return session.createQuery(query).getResultList();
        } finally {
            session.close();
        }
    }
}

