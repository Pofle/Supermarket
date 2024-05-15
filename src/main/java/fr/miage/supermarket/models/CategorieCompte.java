package fr.miage.supermarket.models;

public enum CategorieCompte {
	// Rôle du gérant
	GESTIONNAIRE(),
	// Rôle visiteur (non-connecté)
	VISITEUR(),
	// Rôle utilisateur (connecté)
	UTILISATEUR()
}