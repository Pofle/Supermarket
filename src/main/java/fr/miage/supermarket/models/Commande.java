package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;

@Entity
@Table(name="COMMANDE", uniqueConstraints = {@UniqueConstraint(columnNames= {"Id_Commande"})})
public class Commande {
	@Id
	@Column(name="ID_COMMANDE", nullable=false, unique=true, length=50)
	private String id_commande;
	
	@Column(name="STATUT", nullable=false)
	private boolean statut;

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
	
	@ManyToMany(mappedBy="Commande")
	private Set<Produit> produits = new HashSet<Produit>();
}
