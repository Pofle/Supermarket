import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Session;

import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.HibernateUtil;

public class main {

//	private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	public static void main(String[] args) {
//		Produit article = new Produit();
//		article.setEan("oidfjz");
		//Récupération de la session 
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
//		Session session = sessionFactory.getCurrentSession();
//
//		session.beginTransaction();
//		session.persist(article);
//		try {
//			Commande PanierUn = new Commande(DF.parse("10-05-2024 18:30"));
//			Commande PanierDeux = new Commande(DF.parse("15-05-2024 09:15"));			
//			session.save(PanierUn);
//			session.save(PanierDeux);
//		}
//		catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		}
//		session.getTransaction().commit();
//		System.out.println("Article ID="+article.getEan());
//		
//		Commande wantedCommande = session.load(Commande.class, 1);
//		System.out.println(wantedCommande.getEtat());


		//Ferme la session
		//sessionFactory.close();
	}

}
