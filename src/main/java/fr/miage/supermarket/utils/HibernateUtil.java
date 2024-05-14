package fr.miage.supermarket.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import fr.miage.supermarket.models.Article;

public class HibernateUtil {
	
	private static SessionFactory sessionAnnotationFactory;
	

    private static SessionFactory buildSessionAnnotationFactory() {
    	try {
        	Configuration configuration = new Configuration();
        	configuration.configure("hibernate.cfg.xml");

        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        	configuration.addAnnotatedClass(Article.class);
        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        	
            return sessionFactory;
        }
        catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
	}
    
	public static SessionFactory getSessionAnnotationFactory() {
		if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }
}