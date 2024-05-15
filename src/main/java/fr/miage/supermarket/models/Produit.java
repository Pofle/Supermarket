package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;

@Entity
@Table(name="Produit", uniqueConstraints= {@UniqueConstraint(columnNames= {"EAN"})})
public class Produit {
	
	@Id
	@Column(name="EAN", nullable=false, unique=true, length=13)
	private String ean;
	
	@Column(name="DESCRIPTION_COURTE", length=100, nullable=true)
	private String descriptionCourte;
	
	@Column(name="DESCRIPTION", length=250, nullable=true)
	private String description;
	
	@Column(name="NUTRISCORE", length=1, nullable=true)
	private String nutriscore;
	
	@Column(name="MARQUE", length=50, nullable=true)
	private String marque;
	
	@Column(name="REPERTOIRE_IMAGE", length=150, nullable=true)
	private String repertoireImage;
	
	@Column(name="REPERTOIRE_VIGNETTE", length=150, nullable=true)
	private String repertoireVignette;
	
	@Column(name="LABEL", length=50, nullable=true)
	private String label;
	
	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getDescriptionCourte() {
		return descriptionCourte;
	}

	public void setDescriptionCourte(String descriptionCourte) {
		this.descriptionCourte = descriptionCourte;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNutriscore() {
		return nutriscore;
	}

	public void setNutriscore(String nutriscore) {
		this.nutriscore = nutriscore;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getRepertoireImage() {
		return repertoireImage;
	}

	public void setRepertoireImage(String repertoireImage) {
		this.repertoireImage = repertoireImage;
	}

	public String getRepertoireVignette() {
		return repertoireVignette;
	}

	public void setRepertoireVignette(String repertoireVignette) {
		this.repertoireVignette = repertoireVignette;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	@ManyToMany(mappedBy="Produit")
	private Set<Commande> commandes = new HashSet<Commande>();
}
