package fr.miage.supermarket.models;

import java.util.Set;

import fr.miage.supermarket.xml.CategorieXmlAdapter;
import fr.miage.supermarket.xml.RayonXmlAdapter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(name="CATEGORIE", uniqueConstraints= {@UniqueConstraint(columnNames= {"ID_CATEGORIE"})})
public class Categorie {

	@Id
    @Column(name = "ID_CATEGORIE", nullable = false, unique = true, length = 50)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name="LIBELLE", nullable=false, length=50)
	private String libelle;
	
	@ManyToOne
    @JoinColumn(name = "ID_RAYON", nullable = false)
	private Rayon rayon;
	
	@OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Produit> produits;
	
	@XmlTransient
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	@XmlTransient
	public Set<Produit> getProduits() {
		return produits;
	}

	public void setProduits(Set<Produit> produits) {
		this.produits = produits;
	}

	@XmlJavaTypeAdapter(RayonXmlAdapter.class)
	public Rayon getRayon() {
		return rayon;
	}

	public void setRayon(Rayon rayon) {
		this.rayon = rayon;
	}
}
