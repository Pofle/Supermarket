package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import jakarta.persistence.Column;

@Entity
@Table(name = "COMMANDE", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID_COMMANDE" }) })
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMMANDE", nullable = false, unique = true, length = 50)
    private Integer id_commande;
    
    @OneToMany(mappedBy = "commande")
    @Cascade(CascadeType.ALL)
    private Set<LinkCommandeProduit> produits = new HashSet<>();

    @Column(name = "DATE_COMMANDE")
    private LocalDate dateCommande;

    @Column(name = "DATE_RETRAIT")
    private LocalDate dateRetrait;

    @Column(name = "HORAIRE_RETRAIT")
    private String horaireRetrait;

    @Column(name = "STATUT", nullable = false)
    private boolean statut;

    @ManyToOne
    @JoinColumn(name = "ID_MAGASIN")
    private Magasin magasin;

    @ManyToOne
    @JoinColumn(name = "ID_UTILISATEUR", nullable = false)
    @Cascade(CascadeType.ALL)
    private Utilisateur utilisateur;

    // Constructeurs
    public Commande() {}

    public Commande(int id_commande) {
        this.id_commande = id_commande;
    }

    // Getters et Setters
    public Integer getId_commande() {
        return id_commande;
    }

    public void setId_commande(Integer id_commande) {
        this.id_commande = id_commande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public String getHoraireRetrait() {
        return horaireRetrait;
    }

    public void setHoraireRetrait(String horaireRetrait) {
        this.horaireRetrait = horaireRetrait;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Set<LinkCommandeProduit> getProduits() {
        return produits;
    }

    public void setProduits(Set<LinkCommandeProduit> produits) {
        this.produits = produits;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setIdMagasin(int magasinId) {
        if (this.magasin == null) {
            this.magasin = new Magasin();
        }
        this.magasin.setId(magasinId);
    }

    public void finaliserCommande(float montantTotal) {
        int pointsGagnes = calculerPointsGagnes(montantTotal);
        utilisateur.ajouterPoints(pointsGagnes);
    }

    private int calculerPointsGagnes(float montantTotal) {
        return (int) (montantTotal / 5);
    }
}