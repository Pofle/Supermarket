package fr.miage.supermarket.dto;

public class ProduitDTO {

	private String ean;

	private String libelle;

	private String nutriscore;

	private String marque;

	private String repertoireVignette;

	private String label;

	private Float prix;

	private String conditionnement;

	private Integer quantiteConditionnement;

	private Float poids;

	private int quantiteCommandee;
	
	private Float tauxPromotion;

	private CategorieDTO categorie;
	
	private RayonDTO rayon;

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
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

	public Integer getQuantiteConditionnement() {
		return quantiteConditionnement;
	}

	public void setQuantiteConditionnement(Integer quantiteConditionnement) {
		this.quantiteConditionnement = quantiteConditionnement;
	}

	public Float getPoids() {
		return poids;
	}

	public void setPoids(Float poids) {
		this.poids = poids;
	}

	public int getQuantiteCommandee() {
		return quantiteCommandee;
	}

	public void setQuantiteCommandee(int quantiteCommandee) {
		this.quantiteCommandee = quantiteCommandee;
	}

	public CategorieDTO getCategorie() {
		return categorie;
	}

	public void setCategorie(CategorieDTO categorie) {
		this.categorie = categorie;
	}

	public RayonDTO getRayon() {
		return rayon;
	}

	public void setRayon(RayonDTO rayon) {
		this.rayon = rayon;
	}

	public Float getTauxPromotion() {
		return tauxPromotion;
	}

	public void setTauxPromotion(Float tauxPromotion) {
		this.tauxPromotion = tauxPromotion;
	}
	
	
}
