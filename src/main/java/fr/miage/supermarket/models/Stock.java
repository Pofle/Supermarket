package fr.miage.supermarket.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "STOCK", uniqueConstraints= {@UniqueConstraint(columnNames= {"ID_STOCK"})})
public class Stock {

    @Id
    @Column(name = "ID_STOCK", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "DATE_STOCK", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateStock;
    
    @OneToMany(mappedBy = "stock")
    private List<Link_Produit_Stock> linkProduitStocks;

    // getters et setters
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateStock() {
		return dateStock;
	}

	public void setDateStock(Date dateStock) {
		this.dateStock = dateStock;
	}

	public List<Link_Produit_Stock> getLinkProduitStocks() {
		return linkProduitStocks;
	}

	public void setLinkProduitStocks(List<Link_Produit_Stock> linkProduitStocks) {
		this.linkProduitStocks = linkProduitStocks;
	}    
  
}
