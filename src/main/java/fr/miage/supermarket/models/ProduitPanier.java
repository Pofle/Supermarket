package fr.miage.supermarket.models;

/**
 * Objet représentant le produit dans le panier
 */
public class ProduitPanier {

	private String libelle;
	
	private String ean;
	
	private int quantite;

	public ProduitPanier(String libelle, String ean, int quantite) {
		this.libelle = libelle;
		this.ean = ean;
		this.quantite = quantite;
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
	
	public void ajouterQuantite(int qtt) {
		this.quantite+=qtt;
	}
	
	public void retirerQuantite(int qtt) {
		this.quantite-=qtt;
	}

	@Override
	public String toString() {
		return "ProduitPanier [libelle=" + libelle + ", ean=" + ean + ", quantite=" + quantite + "]";
	}
}
