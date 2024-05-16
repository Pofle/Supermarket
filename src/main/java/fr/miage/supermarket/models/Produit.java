package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.CascadeType;

@Entity
@Table(name="PRODUIT", uniqueConstraints= {@UniqueConstraint(columnNames= {"EAN"})})
public class Produit {
	
	@Id
	@Column(name="EAN", nullable=false, unique=true, length=13)
	private String ean;
	
	@Column(name="LIBELLE", nullable=false, length=100)
	private String libelle;
	
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

	@Column(name="PRIX", nullable=false)
	private Float prix;
	
	@Column(name="CONDITIONNEMENT", length=50, nullable=true)
	private String conditionnement;
	
	@Column(name="POIDS", nullable=true)
	private Float poids;
	
	@ManyToMany(mappedBy = "produits", fetch = FetchType.EAGER)
	private List<Promotion> promotions;
	
	@Transient
	private String vignetteBase64;
	
	@Transient
	private String imageBase64;
	
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
	
	public Float getPrix() {
		return prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	public String getConditionnement() {
		return conditionnement;
	}

	public void setConditionnement(String conditionnement) {
		this.conditionnement = conditionnement;
	}

	public Float getPoids() {
		return poids;
	}

	public void setPoids(Float poids) {
		this.poids = poids;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public List<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}

	public String getVignetteBase64() {
		return vignetteBase64;
	}

	public void setVignetteBase64(String vignetteBase64) {
		this.vignetteBase64 = vignetteBase64;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
}
