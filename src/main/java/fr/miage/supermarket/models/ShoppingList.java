package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.List;
/**
 * Classe d'objet d'une liste de course 
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
	
    /**
     * Constructeur par défaut
     * @author Pauline
     */
    public ShoppingList() {
    }

    /**
     * Constructeur paramétré
     * @param name, nom de la liste
     * @author Pauline
     */
    public ShoppingList(String name) {
        this.name = name;
    }

    /**
     * Getter de l'id de la liste
     * @return id liste
     * @author Pauline
     */
    public int getId() {
        return id;
    }  
    /**
     * Getter du nom de la liste
     * @return nom de la liste
     * @author Pauline
     */
    public String getName() {
        return name;
    }
    /**
     * Getter de l'utilisateur a qui apprtient la liste
     * @return utilisateur linked à la liste
     * @author Pauline
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    /**
     * Setter du nom de la liste
     * @param name de la liste
     * @author Pauline
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Setter de l'utilisateur linked à la liste
     * @param utilisateur linked à la liste
     * @author Pauline
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}