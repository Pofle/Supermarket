package fr.miage.supermarket.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe représentant le produit dans le panier enregistré en session
 * @author EricB
 */
public class ProduitPanier {

	private String libelle;

	private String ean;

	private String conditionnement;

	private Float prix;

	private int quantite;

	private Float tauxPromotion;
	
	private Float poids;
	
	private String image;

	public ProduitPanier(String libelle, String ean, int quantite, Float prix, Float tauxPromotion,
			String conditionnement, Float poids, String image) {
		this.libelle = libelle;
		this.ean = ean;
		this.quantite = quantite;
		this.prix = new BigDecimal(prix).setScale(2, RoundingMode.HALF_UP).floatValue();
		this.tauxPromotion = tauxPromotion;
		this.conditionnement = conditionnement;
		this.poids = poids;
		this.image = image;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public int ajusterQuantite(int qtt) {
		this.quantite += qtt;
		return this.quantite;
	}

	public String getConditionnement() {
		return conditionnement;
	}

	public void setConditionnement(String conditionnement) {
		this.conditionnement = conditionnement;
	}

	public Float getPrix() {
		return prix;
	}

	public void setPrix(Float prix) {
		this.prix = new BigDecimal(prix).setScale(2, RoundingMode.HALF_UP).floatValue();
	}

	public Float getTauxPromotion() {
		return tauxPromotion;
	}

	public void setTauxPromotion(Float tauxPromotion) {
		this.tauxPromotion = tauxPromotion;
	}
	
	public Float getPoids() {
		return poids;
	}

	public void setPoids(Float poids) {
		this.poids = poids;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
