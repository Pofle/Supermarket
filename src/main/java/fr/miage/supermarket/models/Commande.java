package fr.miage.supermarket.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

@Entity (name="commande")

public class Commande implements Serializable{
	/*------Properties------*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ID_COMMANDE")
	private long id_commande;
	
	@Column(name="TEMPS_PREPARATION")
	@Temporal(jakarta.persistence.TemporalType.TIME)
	private String chrono;

	@Column (name="CRENEAU")
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Timestamp creneau;
	
	@Transient 
	private String etat; 
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "contenir", joinColumns = @JoinColumn(name = "ID_COMMANDE"),inverseJoinColumns = @JoinColumn(name = "EAN"))
	private Set<Produit> panier = new HashSet(0);
	
	/*------Constructor------*/
	public Commande() {
		// TODO Auto-generated constructor stub
	}
	public Commande(Timestamp creneau) {
		super();
		this.creneau = creneau;
		etat = "En cours";
	}
	/*------Functions------*/

	public Timestamp getCreneau() {
		return creneau;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Set<Produit> getPanier() {
		return panier;
	}

	public long getIdCommande() {
		return id_commande;
	}

	public String getChrono() {
		return chrono;
	}
	public void setChrono(String chrono) {
		this.chrono = chrono;
	}
	
}

