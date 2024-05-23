package fr.miage.supermarket.models;

import jakarta.persistence.*;

/**
 * Classe représentant le lien entre un produit et un stock dans un magasin.
 * Cette classe est une entité JPA mappée sur la table "LINK_PRODUIT_STOCK".
 * Elle contient les informations sur la quantité de produits en stock,
 * ainsi que les relations avec les entités {@link Stock}, {@link Produit}, et {@link Magasin}.
 * @author AlexP
 */
@Entity
@Table(name = "LINK_PRODUIT_STOCK")
public class Link_Produit_Stock {

    /**
     * Identifiant unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LINK_PRODUIT_STOCK", nullable = false, unique = true, length = 50)
    private Long id;
    
    /**
     * Quantité de produits en stock.
     */
    @Column(name = "QUANTITE", nullable = false, columnDefinition = "int default 1")
    private int quantite;
        
    /**
     * Stock associé au lien produit-stock.
     */
    @ManyToOne
    @JoinColumn(name = "ID_STOCK", nullable = false)
    private Stock stock;
    
    /**
     * Produit associé au lien produit-stock.
     */
    @ManyToOne
    @JoinColumn(name = "EAN", nullable = false)
    private Produit produit;
    
    /**
     * Magasin associé au lien produit-stock.
     */
    @ManyToOne
    @JoinColumn(name = "ID_MAGASIN", nullable = false)
    private Magasin magasin;
    
    /**
     * Constructeur par défaut.
     */
    public Link_Produit_Stock() {}

    /**
     * Constructeur avec paramètres.
     * 
     * @param id l'identifiant du lien produit-stock
     * @param quantite la quantité de produits en stock
     * @param stock le stock associé
     * @param produit le produit associé
     * @param magasin le magasin associé
     */
    public Link_Produit_Stock(Long id, int quantite, Stock stock, Produit produit, Magasin magasin) {
        super();
        this.id = id;
        this.quantite = quantite;
        this.stock = stock;
        this.produit = produit;
        this.magasin = magasin;
    }

    /**
     * Retourne l'identifiant du lien produit-stock.
     * @return l'identifiant du lien produit-stock
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant du lien produit-stock.
     * @param id l'identifiant du lien produit-stock
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne la quantité de produits en stock.
     * @return la quantité de produits en stock
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Définit la quantité de produits en stock.
     * @param quantite la quantité de produits en stock
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Retourne le stock associé au lien produit-stock.
     * @return le stock associé
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * Définit le stock associé au lien produit-stock.
     * @param stock le stock associé
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    /**
     * Retourne le produit associé au lien produit-stock.
     * @return le produit associé
     */
    public Produit getProduit() {
        return produit;
    }

    /**
     * Définit le produit associé au lien produit-stock.
     * @param produit le produit associé
     */
    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    /**
     * Retourne le magasin associé au lien produit-stock.
     * @return le magasin associé
     */
    public Magasin getMagasin() {
        return magasin;
    }

    /**
     * Définit le magasin associé au lien produit-stock.
     * @param magasin le magasin associé
     */
    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }
}
