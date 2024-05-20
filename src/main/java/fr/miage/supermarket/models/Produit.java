package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.io.IOException;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import fr.miage.supermarket.utils.ImageUtil;
import fr.miage.supermarket.xml.CategorieXmlAdapter;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import jakarta.persistence.FetchType;

import java.util.List;


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
	
	@Column(name="CONDITIONNEMENT", length=50, nullable=false)
	private String conditionnement;
	
	@Column(name="QUANTITE_CONDITIONNEMENT", length=50, nullable=true)
	private Integer quantiteConditionnement;
	
	@Column(name="POIDS", nullable=true)
	private Float poids;
	
	// Relations
	
	@ManyToOne
    @JoinColumn(name = "ID_CATEGORIE", nullable = false)
	@Cascade(CascadeType.ALL)
	private Categorie categorie;
	
	@ManyToMany(mappedBy = "produits", fetch = FetchType.EAGER)
	private List<Promotion> promotions;
	
	@ManyToMany(mappedBy = "produits")
	private List<ShoppingList> listes;
	
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
		return this.prix;
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

	public Integer getQuantiteConditionnement() {
		return quantiteConditionnement;
	}

	public void setQuantiteConditionnement(Integer quantiteConditionnement) {
		this.quantiteConditionnement = quantiteConditionnement;
	}

	public String getImageBase64() {
		return imageBase64;
	}
	
	/**
	 * Saisit la base64 de l'image contenue dans le répertoire image du produit
	 * @param fullPath le chemin complet où chercher l'image
	 * @author EricB
	 */
	public void setImageBase64(String fullPath) {
		try {
			this.imageBase64 = ImageUtil.writeImageToBase64(this.repertoireImage , fullPath + this.repertoireImage);
		} catch (IOException e){
			this.imageBase64 = "";
		}
	}

	@XmlTransient
	public List<ShoppingList> getListes() {
		return listes;
	}

	public void setListes(List<ShoppingList> listes) {
		this.listes = listes;
	}

	@XmlJavaTypeAdapter(CategorieXmlAdapter.class)
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
}
