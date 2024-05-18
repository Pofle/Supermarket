package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


/**
 * Object class for the Shopping List
 * @author PaulineF
 */

@Entity
@Table(name = "ShoppingList",uniqueConstraints= {@UniqueConstraint(columnNames= {"ID"})})
public class ShoppingList {
	//Attributs
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true, length=13)
	private int id;
	
	@Column(name="NAME", nullable=false, unique=false, length=50)
    private String name;
	
	//Relation
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTILISATEUR_ID")
    private Utilisateur utilisateur;

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
}
