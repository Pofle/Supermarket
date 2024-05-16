package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;

@Entity
@Table(name = "COMMANDE", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID_COMMANDE" }) })
public class Commande {
	@Id
	@Column(name = "ID_COMMANDE", nullable = false, unique = true, length = 50)
	private int id_commande;
	
	@OneToMany(mappedBy = "commande")
    private Set<LinkCommandeProduit> produits = new HashSet<>();

	@Column(name = "STATUT", nullable = false)
	private boolean statut;

	public int getId_commande() {
		return id_commande;
	}

	public void setId_commande(int id_commande) {
		this.id_commande = id_commande;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	// @ManyToMany(mappedBy="commandes")

	
	
}
