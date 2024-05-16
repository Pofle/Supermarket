package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "STOCK", uniqueConstraints= {@UniqueConstraint(columnNames= {"ID_STOCK"})})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "EAN", nullable = false)
//    private Produit produit;
  
//  @ManyToMany
//  @Column(name = "QUANTITE", nullable = false)
//  private int quantite;
    
    @Column(name = "DATE_STOCK", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @OneToMany(mappedBy = "stock")
    private List<Link_Produit_Stock> linkProduitStocks;

    // getters et setters
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Link_Produit_Stock> getLinkProduitStocks() {
		return linkProduitStocks;
	}

	public void setLinkProduitStocks(List<Link_Produit_Stock> linkProduitStocks) {
		this.linkProduitStocks = linkProduitStocks;
	}    
}
