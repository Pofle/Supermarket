package fr.miage.supermarket.models;

import java.util.List;

import jakarta.persistence.*;

/**
 * Classe d'implémentation de l'entité magasin
 * @author YassineA
 */
@Entity
@Table(name = "MAGASIN", uniqueConstraints= {@UniqueConstraint(columnNames= {"ID_MAGASIN"})})
public class Magasin {

    @Id
    @Column(name = "ID_MAGASIN", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "adresse")
    private String adresse;
    
    @OneToMany(mappedBy = "magasin")
    private List<Link_Produit_Stock> linkProduitStocks;

    // Constructeurs, getters et setters

    public Magasin() {
    }

    public Magasin(int id, String nom, String adresse) {
    	this.id = id;
        this.nom = nom;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public List<Link_Produit_Stock> getLinkProduitStocks() {
		return linkProduitStocks;
	}

	public void setLinkProduitStocks(List<Link_Produit_Stock> linkProduitStocks) {
		this.linkProduitStocks = linkProduitStocks;
	}
}