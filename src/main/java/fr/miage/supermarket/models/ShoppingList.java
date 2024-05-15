package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Set;

/**
 * Object class for the Shopping List
 * @author PaulineF
 */

@Entity
@Table(name = "ShoppingList",uniqueConstraints= {@UniqueConstraint(columnNames= {"EAN"})})
public class ShoppingList {
	//Attributs
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true, length=13)
	private int id;
	
	@Column(name="NAME", nullable=false, unique=false, length=50)
    private String name;

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

    // Setters
    public void setName(String name) {
        this.name = name;
    }

//    // toString method
//    @Override
//    public String toString() {
//        return "ShoppingList [id=" + id + ", name=" + name + "]";
//    }

}
