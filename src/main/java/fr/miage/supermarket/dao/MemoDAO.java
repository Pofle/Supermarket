/**
 * 
 */
package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.miage.supermarket.models.Memo;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * 
 */
public class MemoDAO {
	
	public static List<Memo> getMemosIds(List<ShoppingList> shoppingLists) {
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

}
