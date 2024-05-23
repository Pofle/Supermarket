package fr.miage.supermarket.models;
/**
 * Enumération de la colonne statut de l'entité commande
 * @author RR
 */
public enum StatutCommande {
	// panier non validé 
	NON_VALIDE(),
	// Validé en cours de préparation
	EN_COURS(),
	// préparation terminé
	PRET(),
	// Date de retrait passée
	TERMINE()
}
