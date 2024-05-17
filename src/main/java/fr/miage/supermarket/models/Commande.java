package fr.miage.supermarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_magasin")
    private Magasin magasin;

    @Column(name = "date_commande")
    private LocalDate dateCommande;

    @Column(name = "date_retrait")
    private LocalDate dateRetrait;

    @Column(name = "horaire_retrait")
    private String horaireRetrait;

    // Constructeurs, getters et setters

    public Commande() {
    }

    public Commande(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setIdMagasin(int magasinId) {
        if (this.magasin == null) {
            this.magasin = new Magasin();
        }
        this.magasin.setId(magasinId);
    }
}
