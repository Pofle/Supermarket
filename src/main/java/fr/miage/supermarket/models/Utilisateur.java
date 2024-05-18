package fr.miage.supermarket.models;

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
 * Cllasse de l'objet utilisateur
 * @author PaulineF
 */

@Entity
@Table(name = "Utilisateur",uniqueConstraints= {@UniqueConstraint(columnNames= {"ID"})})
public class Utilisateur {
	
	//Attributs
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true, length=13)
	private int id;
	
	@Column(name = "NOM", nullable = false, unique = false, length = 50)
	private String nom;
	
	@Column(name = "PRENOM", nullable = false, unique = false, length = 80)
	private String prenom;
	
	@Column(name = "MAIL", nullable = false, unique = false, length = 80)
	private String mail;
	
	@Column(name= "ROLE")
	@Enumerated(EnumType.STRING)
	private CategorieCompte role;
	
	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<ShoppingList> listesCourseLst;

	/**
	 * Getter de l'id utilisateur
	 * @return
	 * @author Pauline
	 */
	 public int getId() {
	        return id;
	}
	 /**
	  * Getter du nom utilisateur
	  * @return
	  * @author Pauline
	  */
	public String getNom() {
		return nom;
	}
	/**
	 * Getter prenom utilisateur
	 * @return
	 * @author Pauline
	 */
	public String getPrenom() {
		return prenom;
	}
	/**
	 * Getter mail utilisateur
	 * @return
	 * @author Pauline
	 */
	public String getMail() {
		return mail;
	}
	/*
	 * Getter de la relation avec une liste de course
	 */
	public List<ShoppingList> getListesCourse() {
        return listesCourseLst;
    }
	
	/**
	 * Setter du nom utilisateur
	 * @param nom
	 * @author Pauline
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * Setter prenom utlisateur
	 * @param prenom
	 * @author Pauline
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	/**
	 * Setter mail utilisateur
	 * @param mail
	 * @author Pauline
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * Setter de la relation avec une liste de course
	 * @param listesCourseLst
	 * @author Pauline
	 */
	public void setListeCourse(List<ShoppingList> listesCourseLst) {
        this.listesCourseLst = listesCourseLst;
    }

}
