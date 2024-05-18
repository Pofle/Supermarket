package fr.miage.supermarket.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class Panier {

	private HashMap<String, ProduitPanier> panier = new HashMap<>();

	public void ajusterProduit(String ean, int quantite) {
		if (ean == null || !produitExiste(ean)) {
			return;
		}
		int nouvelleQtt = panier.get(ean).ajusterQuantite(quantite);
		if (nouvelleQtt <= 0) {
			retirerProduit(ean);
			return;
		}
	}
	
	/**
	 * Ajoute le produit, au panier
	 * @param produitPanier
	 */
	public void ajouterProduit(ProduitPanier produitPanier) {
		if (produitPanier == null) {
			return;
		}
		
		if (panier.get(produitPanier.getEan()) == null) {
			panier.put(produitPanier.getEan(), produitPanier);
		}
	}
	
	public void retirerProduit(String ean) {
		panier.remove(ean);
	}
	
	public boolean produitExiste(String ean) {
		return this.panier.containsKey(ean);
	}

	public Float calculerPrixAvecPromotion(Float tauxPromotion, Float prix) {
		if (tauxPromotion == null) {
			return prix;
		}
		BigDecimal taux = new BigDecimal(tauxPromotion).setScale(2, RoundingMode.HALF_UP).divide(new BigDecimal(100));
	    BigDecimal prixFinal = new BigDecimal(prix).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.ONE.subtract(taux));
		return prixFinal.floatValue();
	}
	
	public Float calculerPrixTotal() {
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

	@Override
	public String toString() {
		return "Panier [panier=" + panier + "]";
	}
}
