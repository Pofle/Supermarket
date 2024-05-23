package fr.miage.supermarket.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APPROVISIONNEMENT")
public class Approvisionnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_APPROVISIONNEMENT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EAN", nullable = false)
    private Produit produit;

    @Column(name = "QUANTITE_COMMANDEE", nullable = false)
    private Integer quantiteCommandee;

    @Column(name = "DATE_ARRIVEE_STOCK", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateArriveeStock;

    @ManyToOne
    @JoinColumn(name = "ID_MAGASIN", nullable = false)
    private Magasin magasin;

    // Constructeurs, getters et setters

    public Approvisionnement() {
    }

    public Approvisionnement(Produit produit, Integer quantiteCommandee, Date dateArriveeStock, Magasin magasin) {
        this.produit = produit;
        this.quantiteCommandee = quantiteCommandee;
        this.dateArriveeStock = dateArriveeStock;
        this.magasin = magasin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQuantiteCommandee() {
        return quantiteCommandee;
    }

    public void setQuantiteCommandee(Integer quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }

    public Date getDateArriveeStock() {
        return dateArriveeStock;
    }

    public void setDateArriveeStock(Date dateArriveeStock) {
        this.dateArriveeStock = dateArriveeStock;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }
}

