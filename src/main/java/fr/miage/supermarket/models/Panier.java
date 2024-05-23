package fr.miage.supermarket.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * Classe représentant le panier enregistré en session
 * @author EricB
 */
public class Panier {

	private HashMap<String, ProduitPanier> panier = new HashMap<>();

	/**
	 * Ajuste la quantité pour un produit
	 * @param ean l'ean du produit dont ajuster la quantité
	 * @param quantite la quantité à ajouter/retirer (négatif si retrait)
	 * @author EricB
	 */
	public ProduitPanier ajusterProduit(String ean, int quantite) {
		if (ean == null || !produitExiste(ean)) {
			return null;
		}
		ProduitPanier produitPanier = panier.get(ean);
		int nouvelleQtt = produitPanier.ajusterQuantite(quantite);
		if (nouvelleQtt <= 0) {
			retirerProduit(ean);
		}
		return produitPanier;
	}
	
	/**
	 * Ajoute un produit au panier
	 * @param produitPanier le {@link ProduitPanier} à ajouter
	 * @author EricB
	 */
	public void ajouterProduit(ProduitPanier produitPanier) {
		if (produitPanier == null) {
			return;
		}
		
		if (panier.get(produitPanier.getEan()) == null) {
			panier.put(produitPanier.getEan(), produitPanier);
		}
	}
	
	/**
	 * Retire un produit du panier
	 * @param ean l'ean du produit à retirer du panier
	 * @author EricB
	 */
	public void retirerProduit(String ean) {
		panier.remove(ean);
	}
	
	/**
	 * Renvoit true si le produit dont l'EAN est passé en paramètre existe dans le panier, false sinon.
	 * @param ean l'ean du produit dont vérifier la présence
	 * @return le booleen
	 * @author EricB
	 */
	public boolean produitExiste(String ean) {
		return this.panier.containsKey(ean);
	}

	/**
	 * Calcule le coût d'un produit, quantité et promotion comprise.
	 * @param tauxPromotion le taux de promotion du produit
	 * @param prix le prix du produit
	 * @return un flottant correspondant au prix total du produit
	 * @author EricB
	 */
	public Float calculerPrixAvecPromotion(Float tauxPromotion, Float prix) {
		if (tauxPromotion == null) {
			return prix;
		}
		BigDecimal taux = new BigDecimal(tauxPromotion).setScale(2, RoundingMode.HALF_UP).divide(new BigDecimal(100));
	    BigDecimal prixFinal = new BigDecimal(prix).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.ONE.subtract(taux));
		return prixFinal.floatValue();
	}
	
	/**
	 * Calcule le coût total du panier, quantité et promotion comprises pour chaque produits
	 * @return un flottant correspondant au prix total du panier
	 * @author EricB
	 */
	public float calculerPrixTotal() {
		Float prixTotal = 0f;
		for(ProduitPanier produitPanier: this.panier.values()) {
			prixTotal += calculerPrixAvecPromotion(produitPanier.getTauxPromotion(),
					produitPanier.getPrix() * produitPanier.getQuantite());
		}
		return prixTotal;
	}

	public HashMap<String, ProduitPanier> getPanier() {
		return panier;
	}

	public void setPanier(HashMap<String, ProduitPanier> panier) {
		this.panier = panier;
	}
}
