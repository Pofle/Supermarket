package fr.miage.supermarket.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import fr.miage.supermarket.models.Produit;


/**
 * Classe utilitaire de création de session hibernate
 * @author EricB
 */
public class HibernateUtil {
	
	private static SessionFactory sessionAnnotationFactory;
	
	/**
	 * Méthode de création d'une session hibernate
	 * @return la session nouvellement créée
	 * @author EricB
	 */
    private static SessionFactory buildSessionAnnotationFactory() {
    	try {
        	Configuration configuration = new Configuration();
        	configuration.configure("hibernate.cfg.xml");

        	configuration.addAnnotatedClass(Produit.class);
        	
        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        }
        catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
	}
    
    /**
     * Créer une session si il n'y en pas une déjà instanciée.
     * Renvoit ensuite la session.
     * @return la session
     * @author EricB
     */
	public static SessionFactory getSessionAnnotationFactory() {
		if(sessionAnnotationFactory == null) {
			sessionAnnotationFactory = buildSessionAnnotationFactory();
		}
		return sessionAnnotationFactory;
    }
	
}