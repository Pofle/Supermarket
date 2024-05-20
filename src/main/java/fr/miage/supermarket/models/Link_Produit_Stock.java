package fr.miage.supermarket.models;

import jakarta.persistence.*;

@Entity
@Table(name = "LINK_PRODUIT_STOCK")
public class Link_Produit_Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "ID_LINK_PRODUIT_STOCK", nullable=false, unique=true, length=50)
    private Long id;
    
    @Column (name="QUANTITE", nullable=false, columnDefinition = "bigint default 1")
    private Long quantite;
        
    @ManyToOne
    @JoinColumn(name = "ID_STOCK", nullable=false)
    private Stock stock;
    
    @ManyToOne
    @JoinColumn(name = "EAN", nullable=false)
    private Produit produit;
    
    @ManyToOne
    @JoinColumn(name = "ID_MAGASIN", nullable=false)
    private Magasin magasin;
    
    public Link_Produit_Stock() {}

	public Link_Produit_Stock(Long id, Long quantite, Stock stock, Produit produit, Magasin magasin) {
		super();
		this.id = id;
		this.quantite = quantite;
		this.stock = stock;
		this.produit = produit;
		this.magasin = magasin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQunatite() {
		return quantite;
	}

	public void setQunatite(Long quantite) {
		this.quantite = quantite;
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

	public Magasin getMagasin() {
		return magasin;
	}

	public void setMagasin(Magasin magasin) {
		this.magasin = magasin;
	}
	
}
