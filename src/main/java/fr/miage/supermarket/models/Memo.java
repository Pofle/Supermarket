/**
 * 
 */
package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * 
 */

@Entity
@Table(name = "MEMO",uniqueConstraints= {@UniqueConstraint(columnNames= {"ID"})})
public class Memo {
	//Attributs
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="ID", nullable=false, unique=true, length=13)
	private int id;
	
		@Column(name="LIBELLE", nullable=false, unique=false, length=50)
	private String libelle;
		
		// Relations
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "LISTE_COURSE_ID")
	    private ShoppingList shoppingList;
	    
	   /**
	    * Constructeur par défaut
	    * @author Pauline
	    */
	    public Memo() {
	    }
	    
	    /**
	     * Constructeur paramétré
	     * @param libelle
	     */
	    public Memo(String libelle) {
	        this.libelle = libelle;
	    }

	    /**
	     * Getter de l'identifiant de la ligne
	     * @return, l'identitifiant de la ligne
	     * @author Pauline
	     */
	    public int getId() {
	        return id;
	    }
	    
	    /**
	     * Gette du libelle du memo
	     * @return 
	     * @author Pauline
	     */
	    public String getLibelle() {
	        return libelle;
	    }
	    
	    /**
	     * Getter de l'objet liste de course 
	     * @return
	     * @author Pauline
	     */
	    public ShoppingList getShoppingList() {
	        return shoppingList;
	    }
	    
	    /**
	     * Setter du libelle du memo
	     * @param libelle
	     */
	    public void setLibelle(String libelle) {
	        this.libelle = libelle;
	    }
	    
	    /**
	     * Setter de de l'objet liste de course
	     * @param shoppingList
	     */
	    public void setShoppingList(ShoppingList shoppingList) {
	        this.shoppingList = shoppingList;
	    }

}
