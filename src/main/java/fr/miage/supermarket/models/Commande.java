package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;

@Entity
@Table(name = "COMMANDE", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID_COMMANDE" }) })
public class Commande {
	@Id
    @Column(name = "ID_COMMANDE", nullable = false, unique = true, length = 50)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_commande;
	
    @OneToMany(mappedBy = "commande")
    @Cascade(CascadeType.ALL)
    private Set<LinkCommandeProduit> produits = new HashSet<>();

	@Column(name="STATUT", nullable=false)
	private boolean statut;
	
	@ManyToOne
    @JoinColumn(name = "ID_UTILISATEUR", nullable = false)
	@Cascade(CascadeType.ALL)
    private Utilisateur utilisateur;

	public void finaliserCommande(float montantTotal) {
		int pointsGagnes = calculerPointsGagnes(montantTotal);
		utilisateur.ajouterPoints(pointsGagnes);
	}
	
	private int calculerPointsGagnes(float montantTotal) {
		return (int) (montantTotal / 5);
	}
	
	public Integer getId_commande() {
		return id_commande;
	}

	public void setId_commande(Integer id_commande) {
		this.id_commande = id_commande;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	public Set<LinkCommandeProduit> getProduits() {
		return produits;
	}

	public void setProduits(Set<LinkCommandeProduit> produits) {
		this.produits = produits;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
}
