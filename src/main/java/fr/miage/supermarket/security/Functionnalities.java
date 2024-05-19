package fr.miage.supermarket.security;

import fr.miage.supermarket.models.CategorieCompte;

/**
 * Enumération des fonctionnalités contrôlées et restreintes au sein de l'application
 * @author EricB
 */
public enum Functionnalities {
	
	LOGIN("/login", CategorieCompte.VISITEUR),
	GESTION_PRODUIT("/gestionProduit", CategorieCompte.GESTIONNAIRE),
	GESTION_STOCK("/gestionStock", CategorieCompte.GESTIONNAIRE),
	GESTION_COMMANDE("/gestionCommande", CategorieCompte.GESTIONNAIRE),
	STATISTIQUES("/statistiques", CategorieCompte.GESTIONNAIRE),
	PREPARATION_PANIER("/preparationPanier", CategorieCompte.PREPARATEUR),
	GERER_LISTE("/gestionList", CategorieCompte.UTILISATEUR, CategorieCompte.VISITEUR),
	RAYONS("/rayons", CategorieCompte.UTILISATEUR, CategorieCompte.VISITEUR),
	PROMOS("/promos", CategorieCompte.UTILISATEUR, CategorieCompte.VISITEUR),
	CONSULTER_LISTE_COURSES("/listeCourse", CategorieCompte.UTILISATEUR, CategorieCompte.VISITEUR);	
	
	private final String chemin;
	
	private final CategorieCompte[] rolesAutorises;
	
	Functionnalities(String chemin, CategorieCompte... rolesAutorises) {
		this.chemin = chemin;
		this.rolesAutorises = rolesAutorises;
	}

	public String getChemin() {
		return chemin;
	}

	public CategorieCompte[] getRolesAutorises() {
		return rolesAutorises;
	}
}
