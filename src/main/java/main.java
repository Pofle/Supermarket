import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.HibernateUtil;

public class main {

	public static void main(String[] args) {
//		Produit article = new Produit();
//		article.setEan("oidfjz");
//		//Récupération de la session 
//		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
//		Session session = sessionFactory.getCurrentSession();
		
//		
//		 int testListeId = 3;
//
//	        try {
//	            List<LinkListeProduit> linkListeProduits = getLinkListProduit(testListeId);
//
//	            for (LinkListeProduit link : linkListeProduits) {
//	                System.out.println("Produit Libellé: " + link.getProduit().getLibelle() +
//	                                   ", Quantité: " + link.getQuantite());
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }
//	
//	public static List<LinkListeProduit> getLinkListProduit(int listeId) throws Exception {
//        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
//        Transaction tx = null;
//        List<LinkListeProduit> linkListeProduits = new ArrayList<>();
//
//        try {
//            tx = session.beginTransaction();
//            Query<LinkListeProduit> query = session.createQuery(
//                    "from LinkListeProduit pl where pl.shoppingList.id = :listeId", LinkListeProduit.class);
//                query.setParameter("listeId", listeId);
//                linkListeProduits = query.list();
//                tx.commit();
//        } catch (Exception e) {
//            if (tx != null) tx.rollback();
//            throw e;
//        } finally {
//            session.close();
//        }
//        return linkListeProduits;
//    }

}
