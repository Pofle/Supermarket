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
/**
 * Classe d'objet de la relation entre liste de course et produits
 */
@Entity
@Table(name = "LINK_LISTE_PRODUIT")
public class LinkListeProduit {
		
	//Attributs
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Long id;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "ID_LISTE", referencedColumnName = "ID")
	    private ShoppingList shoppingList;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "EAN", referencedColumnName = "EAN")
	    private Produit produit;

	    @Column(name = "QUANTITE")
	    private int quantite;

	    /**
	     * Constructeur par defaut
	     */
	    public LinkListeProduit() {
	    }
	    /**
	     * Constructeur paramétré
	     * @param shoppingList 
	     * @param produit
	     * @param quantite ajoutee a la liste
	     * @author Pauline
	     * 
	     */
	    public LinkListeProduit(ShoppingList shoppingList, Produit produit, int quantite) {
	        this.shoppingList = shoppingList;
	        this.produit = produit;
	        this.quantite = quantite;
	    }
	   /**
	    * Getter de la liste de course
	    * @return
	    *@author Pauline
	    */
		public ShoppingList getShoppingList() {
			return shoppingList;
		}
		
		/**
		 * Getter du produit lie a une liste de course
		 * @return
		 * @author Pauline
		 */
		public Produit getProduit() {
			return produit;
		}
		/**
		 * Getter de la quantite d'un produit dans une liste d course
		 * @return
		 * @author Pauline
		 */
		public int getQuantite() {
			return quantite;
		}	
		/**
		 * Getter de l'id de la ligne d'un produit dans une liste
		 * @return
		 * @author Pauline
		 */
		public Long getId() {
			return id;
		}
		/**
		 * Setter de la liste de course
		 * @param shoppingList
		 * @author Pauline
		 */
		public void setShoppingList(ShoppingList shoppingList) {
			this.shoppingList = shoppingList;
		}
		/**
		 * Setter du produit lié à la liste de course
		 * @param produit
		 * @author Pauline
		 */
		public void setProduit(Produit produit) {
			this.produit = produit;
		}
		/**
		 * Setter de la quantité d'un produit dans une liste de course
		 * @param quantite
		 * @author Pauline
		 */
		public void setQuantite(int quantite) {
			this.quantite = quantite;
		}	    
}
