package fr.miage.supermarket.models;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "RAYON", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID_RAYON" }) })
public class Rayon {

	@Id
	@Column(name = "ID_RAYON", nullable = false, unique = true, length = 50)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "LIBELLE", nullable = false, length = 50)
	private String libelle;

	@OneToMany(mappedBy = "rayon", cascade = CascadeType.ALL)
	private Set<Categorie> categories;

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

	public Set<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(Set<Categorie> categories) {
		this.categories = categories;
	}
}
