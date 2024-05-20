package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import jakarta.persistence.Column;

@Entity
@Table(name="COMMANDE", uniqueConstraints = {@UniqueConstraint(columnNames= {"Id_Commande"})})
public class Commande {
	
	@Id
	@Column(name="ID_COMMANDE", nullable=false, unique=true, length=50)
	private String id_commande;
	
	@Column(name="STATUT", nullable=false)
	private boolean statut;

	@Column(name="TEMPS_PREPARATION")
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private LocalDateTime chrono;
	
	//temporaire 
	@Column (name="CRENEAU", nullable=false)
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Timestamp creneau;

	
	@OneToMany(mappedBy = "commande",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Link_Commande_Produit> produits_panier = new HashSet<>();
	
	public String getId_commande() {
		return id_commande;
	}

	public void setId_commande(String id_commande) {
		this.id_commande = id_commande;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}
	
	public Set<Link_Commande_Produit> getProduits_panier() {
        return produits_panier;
    }

    public void setProduits_panier(Set<Link_Commande_Produit> produits_panier) {
        this.produits_panier = produits_panier;
    }

	public LocalDateTime getChrono() {
		return chrono;
	}

	public void setChrono(LocalDateTime chrono) {
		this.chrono = chrono;
	}

	public Timestamp getCreneau() {
		return creneau;
	}

	public void setCreneau(Timestamp creneau) {
		this.creneau = creneau;
	}
    
    
    
//	@ManyToMany
//	@JoinTable( 
//			name = "LINK_COMMANDE_PRODUIT", 
//			joinColumns = @JoinColumn(name = "ID_COMMANDE"), 
//			inverseJoinColumns = @JoinColumn(name = "EAN") 
//	)
//	private Set<Produit> produits_panier = new HashSet<Produit>();
}
