import org.hibernate.SessionFactory;

import java.util.List;

import org.hibernate.Session;

import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.HibernateUtil;

public class main {

	public static void main(String[] args) {
//		Produit article = new Produit();
//		article.setEan("oidfjz");
//		//Récupération de la session 
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
//
//		//session.beginTransaction();
//		//session.persist(article);
//		//session.getTransaction().commit();
//		//System.out.println("Article ID="+article.getEan());
//		
//		//Ferme la session
//		sessionFactory.close();
//	}
		
//		 try {
//	           List<ShoppingList> shoppingLists = ShoppingListDAO.getShoppingLists();
//	            for (ShoppingList list : shoppingLists) {
//	                System.out.println("ID: " + list.getId() + ", Name: " + list.getName());
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }

}}
