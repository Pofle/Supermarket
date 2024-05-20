package fr.miage.supermarket.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * Classe représente la quantité en stock des produits.
 * Cette classe est une entité JPA mappée sur la table "STOCK". 
 * Elle contient les informations sur la date de stock et les relations avec 
 * les entités {@link Link_Produit_Stock}.
 * @author AlexP
 */
@Entity
@Table(name = "STOCK", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID_STOCK"})})
public class Stock {

    /**
     * Identifiant unique du stock.
     */
    @Id
    @Column(name = "ID_STOCK", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Date du stock.
     */
    @Column(name = "DATE_STOCK", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateStock;
    
    /**
     * Liste des liens produit-stock associés à ce stock.
     */
    @OneToMany(mappedBy = "stock")
    private List<Link_Produit_Stock> linkProduitStocks;

    /**
     * Retourne l'identifiant du stock.
     * @return l'identifiant du stock
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant du stock.
     * @param id l'identifiant du stock
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne la date du stock.
     * @return la date du stock
     */
    public Date getDateStock() {
        return dateStock;
    }

    /**
     * Définit la date du stock.
     * @param dateStock la date du stock
     */
    public void setDateStock(Date dateStock) {
        this.dateStock = dateStock;
    }

    /**
     * Retourne la liste des liens produit-stock associés à ce stock.
     * @return la liste des liens produit-stock
     */
    public List<Link_Produit_Stock> getLinkProduitStocks() {
        return linkProduitStocks;
    }

    /**
     * Définit la liste des liens produit-stock associés à ce stock.
     * @param linkProduitStocks la liste des liens produit-stock
     */
    public void setLinkProduitStocks(List<Link_Produit_Stock> linkProduitStocks) {
        this.linkProduitStocks = linkProduitStocks;
    }    
}
