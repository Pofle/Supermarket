package fr.miage.supermarket.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "LINK_COMMANDE_PRODUIT")
public class LinkCommandeProduit {

	@EmbeddedId
	private LinkCommandeProduitId id = new LinkCommandeProduitId();

	@ManyToOne
	@MapsId("commandeId")
	@JoinColumn(name = "ID_COMMANDE", insertable = false, updatable = false)
	private Commande commande;

	@ManyToOne
	@MapsId("produitId")
	@JoinColumn(name = "EAN", insertable = false, updatable = false)
	private Produit produit;

	@Column(name = "QUANTITE")
	private int quantite;
	public LinkCommandeProduit(){}	
	public LinkCommandeProduit(Commande commande, Produit produit, int quantite) {
		super();
		this.commande = commande;
		this.produit = produit;
		this.quantite = quantite;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
}
