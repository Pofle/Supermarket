package fr.miage.supermarket.models;

/**
 * Enumération des catégories de compte disponibles sur l'application
 * @author EricB
 */
public enum CategorieCompte {
	// Rôle du gérant
	GESTIONNAIRE(),
	// Rôle visiteur (non-connecté)
	VISITEUR(),
	// Rôle utilisateur (connecté)
	UTILISATEUR()
}