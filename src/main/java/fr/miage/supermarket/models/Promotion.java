package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="PROMOTION", uniqueConstraints= {@UniqueConstraint(columnNames= {"ID"})})
public class Promotion {

	@Id
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@Column(name="POURCENTAGE", nullable=false)
	private Float pourcentage;
	
	@Column(name="DATE_DEBUT", nullable=false)
	private Date dateDebut;
	
	@Column(name="DATE_FIN", nullable=false)
	private Date dateFin;

	@ManyToMany
	@JoinTable(
	    name = "LINK_PROMOTION_PRODUIT",
	    joinColumns = @JoinColumn(name = "PROMOTION_ID"),
	    inverseJoinColumns = @JoinColumn(name = "PRODUIT_EAN")
	)
	private List<Produit> produits;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getPourcentage() {
		return pourcentage;
	}

	public void setPourcentage(Float pourcentage) {
		this.pourcentage = pourcentage;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
}
