package fr.miage.supermarket.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import fr.miage.supermarket.models.Utilisateur;
import fr.miage.supermarket.utils.HibernateUtil;

public class StatistiquesDAO {
	private SessionFactory sessionFactory;

	public StatistiquesDAO() {
        this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
    }

    public Map<String, Object> getStatistiquesConsommation(Utilisateur utilisateur) {
    	Session session = sessionFactory.openSession();
    	
        Map<String, Object> statistiques = new HashMap<>();

        // Répartition par catégorie
        List<Object[]> categorieStats = session.createQuery(
                "SELECT p.categorie.libelle, COUNT(p) FROM Commande c JOIN c.produits cp JOIN cp.produit p " +
                "WHERE c.utilisateur = :utilisateur GROUP BY p.categorie.libelle ORDER BY COUNT(p) DESC", Object[].class)
                .setParameter("utilisateur", utilisateur)
                .getResultList();
        statistiques.put("categories", categorieStats);

        // Répartition par nutriscore
        List<Object[]> nutriscoreStats = session.createQuery(
                "SELECT p.nutriscore, COUNT(p) FROM Commande c JOIN c.produits cp JOIN cp.produit p " +
                "WHERE c.utilisateur = :utilisateur GROUP BY p.nutriscore ORDER BY COUNT(p) DESC", Object[].class)
                .setParameter("utilisateur", utilisateur)
                .getResultList();
        statistiques.put("nutriscores", nutriscoreStats);

        // Répartition par produits bio
        List<Object[]> bioStats = session.createQuery(
                "SELECT p.label, COUNT(p) FROM Commande c JOIN c.produits cp JOIN cp.produit p " +
                "WHERE c.utilisateur = :utilisateur AND p.label = 'BIO' GROUP BY p.label ORDER BY COUNT(p) DESC", Object[].class)
                .setParameter("utilisateur", utilisateur)
                .getResultList();
        statistiques.put("bio", bioStats);

        // Répartition par marque
        List<Object[]> marqueStats = session.createQuery(
                "SELECT p.marque, COUNT(p) FROM Commande c JOIN c.produits cp JOIN cp.produit p " +
                "WHERE c.utilisateur = :utilisateur GROUP BY p.marque ORDER BY COUNT(p) DESC", Object[].class)
                .setParameter("utilisateur", utilisateur)
                .getResultList();
        statistiques.put("marques", marqueStats);

        return statistiques;
    }
    public Map<String, Object> getStatistiquesConsommationUtilisateurs() {
    	Session session = sessionFactory.openSession();
    	
        Map<String, Object> statistiques = new HashMap<>();

        // Répartition par catégorie
        List<Object[]> categorieStats = session.createQuery(
                "SELECT p.categorie.libelle, COUNT(p) FROM Commande c JOIN c.produits cp JOIN cp.produit p " +
                "GROUP BY p.categorie.libelle ORDER BY COUNT(p) DESC", Object[].class)
                .getResultList();
        statistiques.put("categories", categorieStats);

        // Répartition par nutriscore
        List<Object[]> nutriscoreStats = session.createQuery(
                "SELECT p.nutriscore, COUNT(p) FROM Commande c JOIN c.produits cp JOIN cp.produit p " +
                "GROUP BY p.nutriscore ORDER BY COUNT(p) DESC", Object[].class)
                .getResultList();
        statistiques.put("nutriscores", nutriscoreStats);

        // Répartition par produits bio
        List<Object[]> bioStats = session.createQuery(
                "SELECT p.label, COUNT(p) FROM Commande c JOIN c.produits cp JOIN cp.produit p " +
                "WHERE p.label = 'BIO' GROUP BY p.label ORDER BY COUNT(p) DESC", Object[].class)
                .getResultList();
        statistiques.put("bio", bioStats);

        // Répartition par marque
        List<Object[]> marqueStats = session.createQuery(
                "SELECT p.marque, COUNT(p) FROM Commande c JOIN c.produits cp JOIN cp.produit p " +
                "GROUP BY p.marque ORDER BY COUNT(p) DESC", Object[].class)
                .getResultList();
        statistiques.put("marques", marqueStats);

        return statistiques;
    }
}
