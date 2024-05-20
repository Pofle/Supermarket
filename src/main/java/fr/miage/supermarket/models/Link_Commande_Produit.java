package fr.miage.supermarket.models;

import jakarta.persistence.*;

@Entity
@Table(name = "LINK_COMMANDE_PRODUIT")
public class Link_Commande_Produit {
	
    /*------Propriétés------*/
    

    @Column (name="QUANTITE", nullable=false, columnDefinition = "bigint default 1")
    private Long qte;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_COMMANDE", nullable = false)
    private Commande commande;

    @Id
    @ManyToOne
    @JoinColumn(name = "EAN", nullable = false)
    private Produit produit;
    
    /*------Constructeur------*/
    
    public Link_Commande_Produit() {}
    
	public Link_Commande_Produit(Long qte, Commande commande, Produit produit) {
		super();
		this.qte = qte;
		this.commande = commande;
		this.produit = produit;
	}
    
    /*------Getters & Setters------*/
    
//	public LinkCommandeProduitId getId() {
//		return id;
//	}
//	public void setId(LinkCommandeProduitId id) {
//		this.id = id;
//	}
	
	public Long getQte() {
		return qte;
	}
	public void setQte(Long qte) {
		if (qte < 1) {
            throw new IllegalArgumentException("La quantité ne peut pas être inférieure à 1");
		}
		this.qte = qte;
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
}

