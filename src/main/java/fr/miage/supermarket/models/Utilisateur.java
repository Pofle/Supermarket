package fr.miage.supermarket.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Object class for the Shopping List
 * 
 * @author PaulineF
 */

@Entity
@Table(name = "Utilisateur", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
public class Utilisateur {

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private int id;

	@Column(name = "NOM", nullable = false, unique = false, length = 50)
	private String nom;

	@Column(name = "PRENOM", nullable = false, unique = false, length = 80)
	private String prenom;

	@Column(name = "MAIL", nullable = false, unique = false, length = 80)
	private String mail;

	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private CategorieCompte role;

	@Column(name = "MDP", nullable = false, unique = false, length = 80)
	private String motdepasse;
	
	@Column(name="POINTS", nullable = true, length = 50)
	private Integer points;

	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
	private List<ShoppingList> listesCourseLst;

	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
	private List<Commande> commandes = new ArrayList<>();

	// Getters
	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getMail() {
		return mail;
	}

	public List<ShoppingList> getListesCourse() {
		return listesCourseLst;
	}

	// Setters
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public CategorieCompte getRole() {
		return role;
	}

	public void setRole(CategorieCompte role) {
		this.role = role;
	}

	public void setListeCourse(List<ShoppingList> listesCourseLst) {
		this.listesCourseLst = listesCourseLst;
	}

	public List<ShoppingList> getListesCourseLst() {
		return listesCourseLst;
	}

	public void setListesCourseLst(List<ShoppingList> listesCourseLst) {
		this.listesCourseLst = listesCourseLst;
	}

	public List<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public void ajouterPoints(int nbPoints) {
		if(this.points == null) {
			this.points = nbPoints;
			return;
		}
		this.points+=nbPoints;
	}
}
