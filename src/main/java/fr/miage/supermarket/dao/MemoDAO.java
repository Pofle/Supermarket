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

import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.Memo;
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
	
	public static void ajouterLibelle(String libelle, int listeId) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
	    Transaction tx = null;
	    
	    try {
	        tx = session.beginTransaction();
	        Memo memo = new Memo();
	        memo.setLibelle(libelle);

	        // Récupérer la liste de course à partir de l'id
	        ShoppingList shoppingList = session.get(ShoppingList.class, listeId);
	        memo.setShoppingList(shoppingList);

	        session.persist(memo);
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        throw e;
	    } finally {
	        session.close();
	    }
	    System.out.println("Memo successfully added.");
	}
	

}
