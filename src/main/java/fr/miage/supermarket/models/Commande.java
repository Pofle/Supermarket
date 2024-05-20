package fr.miage.supermarket.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Commande", uniqueConstraints = { @UniqueConstraint(columnNames = { "id_commande" }) })
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_commande", nullable = false, unique = true, length = 50)
    private int id_commande;

    @ManyToOne
    @JoinColumn(name = "id_magasin")
    private Magasin magasin;

    @OneToMany(mappedBy = "commande")
    private Set<LinkCommandeProduit> produits = new HashSet<>();

    @Column(name = "date_commande")
    private LocalDate dateCommande;

    @Column(name = "date_retrait")
    private LocalDate dateRetrait;

    @Column(name = "horaire_retrait")
    private String horaireRetrait;

    @Column(name="statut", nullable=false)
    private boolean statut;

    // Constructeurs
    public Commande() {
    }

    public Commande(int id_commande) {
        this.id_commande = id_commande;
    }

    // Getters et Setters
    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
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

    public void setIdMagasin(int magasinId) {
        if (this.magasin == null) {
            this.magasin = new Magasin();
        }
        this.magasin.setId(magasinId);
    }
}
