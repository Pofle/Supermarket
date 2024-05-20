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
 * Cllasse de l'objet utilisateur
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

    @Column(name = "POINTS", nullable = true)
    private Integer points;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<ShoppingList> listesCourseLst;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Commande> commandes;

    // Getters et setters

    /**
     * Getter de l'id utilisateur
     * @return l'id de l'utilisateur
     * @author Pauline
     */
    public int getId() {
        return id;
    }

    /**
     * Setter de l'id utilisateur
     * @param id l'id de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter du nom utilisateur
     * @return le nom de l'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom utilisateur
     * @param nom le nom de l'utilisateur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter prenom utilisateur
     * @return le prenom de l'utilisateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter prenom utilisateur
     * @param prenom le prenom de l'utilisateur
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter mail utilisateur
     * @return le mail de l'utilisateur
     */
    public String getMail() {
        return mail;
    }

    /**
     * Setter mail utilisateur
     * @param mail le mail de l'utilisateur
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Getter du mot de passe
     * @return le mot de passe de l'utilisateur
     */
    public String getMotdepasse() {
        return motdepasse;
    }

    /**
     * Setter du mot de passe
     * @param motdepasse le mot de passe de l'utilisateur
     */
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    /**
     * Getter du role
     * @return le role de l'utilisateur
     */
    public CategorieCompte getRole() {
        return role;
    }

    /**
     * Setter du role
     * @param role le role de l'utilisateur
     */
    public void setRole(CategorieCompte role) {
        this.role = role;
    }

    /**
     * Getter de la relation avec une liste de course
     * @return la liste de courses de l'utilisateur
     */
    public List<ShoppingList> getListesCourse() {
        return listesCourseLst;
    }

    /**
     * Setter de la relation avec une liste de course
     * @param listesCourseLst la liste de courses de l'utilisateur
     */
    public void setListeCourse(List<ShoppingList> listesCourseLst) {
        this.listesCourseLst = listesCourseLst;
    }

    /**
     * Getter de la liste de commandes
     * @return la liste des commandes de l'utilisateur
     */
    public List<Commande> getCommandes() {
        return commandes;
    }

    /**
     * Setter de la liste de commandes
     * @param commandes la liste des commandes de l'utilisateur
     */
    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    /**
     * Getter des points
     * @return les points de l'utilisateur
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Setter des points
     * @param points les points de l'utilisateur
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * Ajouter des points
     * @param nbPoints le nombre de points à ajouter
     */
    public void ajouterPoints(int nbPoints) {
        if (this.points == null) {
            this.points = nbPoints;
            return;
        }
        this.points += nbPoints;
    }
}
