package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LINK_LISTE_PRODUIT")
public class LinkListeProduit {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "ID_LISTE", referencedColumnName = "ID")
	    private ShoppingList shoppingList;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "EAN", referencedColumnName = "EAN")
	    private Produit produit;

	    @Column(name = "QUANTITE")
	    private int quantite;

	    // Constructeurs
	    public LinkListeProduit() {
	    }
	    public LinkListeProduit(ShoppingList shoppingList, Produit produit, int quantite) {
	        this.shoppingList = shoppingList;
	        this.produit = produit;
	        this.quantite = quantite;
	    }
	    //Getter
		public ShoppingList getShoppingList() {
			return shoppingList;
		}
		
		public Produit getProduit() {
			return produit;
		}
		
		public int getQuantite() {
			return quantite;
		}	
		public Long getId() {
			return id;
		}
		// Setters
		public void setShoppingList(ShoppingList shoppingList) {
			this.shoppingList = shoppingList;
		}
		public void setProduit(Produit produit) {
			this.produit = produit;
		}
		public void setQuantite(int quantite) {
			this.quantite = quantite;
		}
	    
	    
}
