package fr.miage.supermarket.models;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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
	
	@OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
	private Set<Produit> produits;
	
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

	public Set<Produit> getProduits() {
		return produits;
	}

	public void setProduits(Set<Produit> produits) {
		this.produits = produits;
	}

	public Rayon getRayon() {
		return rayon;
	}

	public void setRayon(Rayon rayon) {
		this.rayon = rayon;
	}
}
