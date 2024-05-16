package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "LINK_PRODUIT_STOCK")
public class Link_Produit_Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "ID_LINK_PRODUIT_STOCK", nullable=false, unique=true, length=50)
    private Long id;
    
    @Column (name="QUANTITE", nullable=false, columnDefinition = "bigint default 1")
    private Long qunatite;
        
    @ManyToOne
    @JoinColumn(name = "ID_STOCK", nullable=false)
    private Stock stock;
    
    @ManyToOne
    @JoinColumn(name = "EAN", nullable=false)
    private Produit produit;

	public Link_Produit_Stock(Long id, Long qunatite, Stock stock, Produit produit) {
		super();
		this.id = id;
		this.qunatite = qunatite;
		this.stock = stock;
		this.produit = produit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQunatite() {
		return qunatite;
	}

	public void setQunatite(Long qunatite) {
		this.qunatite = qunatite;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
}
