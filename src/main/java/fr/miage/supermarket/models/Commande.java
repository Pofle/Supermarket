package fr.miage.supermarket.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

@Entity (name="Commande")

public class Commande implements Serializable{
	/*------Properties------*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long codeC;
	
	private Date creneau;
	
	@Transient
	private String etat; 
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Contenir", joinColumns = @JoinColumn(name = "codeC"),inverseJoinColumns = @JoinColumn(name = "codeA"))
	private Set<Produit> panier = new HashSet(0);
	
	/*------Constructor------*/
	public Commande() {
		// TODO Auto-generated constructor stub
	}
	/*------Functions------*/

	public Date getCreneau() {
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

	public long getCodeC() {
		return codeC;
	}
	
}

