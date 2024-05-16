package fr.miage.supermarket.models;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Creneau")
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "heure_debut")
    @Temporal(TemporalType.TIME)
    private Date heureDebut;

    @Column(name = "heure_fin")
    @Temporal(TemporalType.TIME)
    private Date heureFin;

    @Column(name = "nombre_max_commandes")
    private int nombreMaxCommandes;

    // Constructeurs, getters et setters

    public Creneau() {
    }

    public Creneau(Date heureDebut, Date heureFin, int nombreMaxCommandes) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.nombreMaxCommandes = nombreMaxCommandes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public int getNombreMaxCommandes() {
        return nombreMaxCommandes;
    }

    public void setNombreMaxCommandes(int nombreMaxCommandes) {
        this.nombreMaxCommandes = nombreMaxCommandes;
    }
    
}

