package fr.miage.supermarket.models;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Classe représente l'approvisionnement des stock des produits.
 * Cette classe est une entité JPA mappée sur la table "APPROVISIONNEMENT". 
 * Elle contient les informations sur les quantités commandées, la date de stock d'arrivée
 * du stock et les relations avec les entités {@link Produit}, et {@link Magasin}.
 * 
 * @see Produit
 * @see Magasin
 * 
 * @author AlexP
 */
@Entity
@Table(name = "APPROVISIONNEMENT")
public class Approvisionnement {

    /**
     * Identifiant unique de l'approvisionnement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_APPROVISIONNEMENT")
    private Long id;

    /**
     * Produit associé à l'approvisionnement.
     */
    @ManyToOne
    @JoinColumn(name = "EAN", nullable = false)
    private Produit produit;

    /**
     * Quantité commandée dans cet approvisionnement.
     */
    @Column(name = "QUANTITE_COMMANDEE", nullable = false)
    private Integer quantiteCommandee;

    /**
     * Date d'arrivée du stock associé à cet approvisionnement.
     */
    @Column(name = "DATE_ARRIVEE_STOCK", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateArriveeStock;

    /**
     * Magasin où l'approvisionnement a été effectué.
     */
    @ManyToOne
    @JoinColumn(name = "ID_MAGASIN", nullable = false)
    private Magasin magasin;

    /**
     * Constructeur par défaut de la classe Approvisionnement.
     */
    public Approvisionnement() {
    }

    /**
     * Constructeur avec paramètres de la classe Approvisionnement.
     * 
     * @param produit            le produit associé à l'approvisionnement
     * @param quantiteCommandee  la quantité commandée
     * @param dateArriveeStock  la date d'arrivée du stock
     * @param magasin            le magasin où l'approvisionnement a été effectué
     */
    public Approvisionnement(Produit produit, Integer quantiteCommandee, Date dateArriveeStock, Magasin magasin) {
        this.produit = produit;
        this.quantiteCommandee = quantiteCommandee;
        this.dateArriveeStock = dateArriveeStock;
        this.magasin = magasin;
    }

 // Getters et setters

    /**
     * Retourne l'identifiant de l'approvisionnement.
     * @return l'identifiant de l'approvisionnement
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'approvisionnement.
     * @param id l'identifiant de l'approvisionnement
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le produit associé à l'approvisionnement.
     * @return le produit associé à l'approvisionnement
     */
    public Produit getProduit() {
        return produit;
    }

    /**
     * Définit le produit associé à l'approvisionnement.
     * @param produit le produit associé à l'approvisionnement
     */
    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    /**
     * Retourne la quantité commandée dans cet approvisionnement.
     * @return la quantité commandée dans cet approvisionnement
     */
    public Integer getQuantiteCommandee() {
        return quantiteCommandee;
    }

    /**
     * Définit la quantité commandée dans cet approvisionnement.
     * @param quantiteCommandee la quantité commandée dans cet approvisionnement
     */
    public void setQuantiteCommandee(Integer quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }

    /**
     * Retourne la date d'arrivée du stock associé à cet approvisionnement.
     * @return la date d'arrivée du stock associé à cet approvisionnement
     */
    public Date getDateArriveeStock() {
        return dateArriveeStock;
    }

    /**
     * Définit la date d'arrivée du stock associé à cet approvisionnement.
     * @param dateArriveeStock la date d'arrivée du stock associé à cet approvisionnement
     */
    public void setDateArriveeStock(Date dateArriveeStock) {
        this.dateArriveeStock = dateArriveeStock;
    }

    /**
     * Retourne le magasin où l'approvisionnement a été effectué.
     * @return le magasin où l'approvisionnement a été effectué
     */
    public Magasin getMagasin() {
        return magasin;
    }

    /**
     * Définit le magasin où l'approvisionnement a été effectué.
     * @param magasin le magasin où l'approvisionnement a été effectué
     */
    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }
}