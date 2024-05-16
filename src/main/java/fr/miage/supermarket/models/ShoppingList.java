package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.List;
/**
 * Object class for the Shopping List
 * @author PaulineF
 */

@Entity
@Table(name = "LISTE_COURSE",uniqueConstraints= {@UniqueConstraint(columnNames= {"ID"})})
public class ShoppingList {
	//Attributs
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true, length=13)
	private int id;
	
	@Column(name="NAME", nullable=false, unique=false, length=50)
    private String name;
	
	//Relations
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTILISATEUR_ID")
    private Utilisateur utilisateur;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "LINK_LISTE_PRODUIT", 
	           joinColumns = @JoinColumn(name = "ID_LISTE"), 
	           inverseJoinColumns = @JoinColumn(name = "EAN",  referencedColumnName = "ean"))
	private List<Produit> produits;

    // Construteur par défaut
    public ShoppingList() {
    }

    // Conbstructeur paramétré
    public ShoppingList(String name) {
        //this.id = id;
        this.name = name;
    }

    // Getters
    public int getId() {
        return id;
    }  
    public String getName() {
        return name;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

//    // toString method
//    @Override
//    public String toString() {
//        return "ShoppingList [id=" + id + ", name=" + name + "]";
//    }

}
