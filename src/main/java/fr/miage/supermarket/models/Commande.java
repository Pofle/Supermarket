package fr.miage.supermarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;

@Entity
@Table(name = "COMMANDE", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID_COMMANDE" }) })
public class Commande {
	
	@Id
    @Column(name = "ID_COMMANDE", nullable = false, unique = true, length = 50)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_commande;
	
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
//    @Cascade(CascadeType.ALL)
    private Set<LinkCommandeProduit> produits = new HashSet<>();

	@Column(name="STATUT", nullable=false)
	private boolean statut;
	
	@Column(name="TEMPS_PREPARATION")
	@Temporal(jakarta.persistence.TemporalType.TIME)
	private Time chrono;
	
	@Column (name="CRENEAU")
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Timestamp creneau;

	@ManyToOne ( cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_UTILISATEUR", nullable = false)
//	@Cascade(CascadeType.ALL)
    private Utilisateur utilisateur;

	public void finaliserCommande(float montantTotal) {
		int pointsGagnes = calculerPointsGagnes(montantTotal);
		utilisateur.ajouterPoints(pointsGagnes);
	}
	
	private int calculerPointsGagnes(float montantTotal) {
		return (int) (montantTotal / 5);
	}
	
	public Integer getId_commande() {
		return id_commande;
	}

	public void setId_commande(Integer id_commande) {
		this.id_commande = id_commande;
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

	public Time getChrono() {
		return chrono;
	}

	public void setChrono(Time chrono) {
		this.chrono = chrono;
	}
	
	public Timestamp getCreneau() {
		return creneau;
	}

	public void setCreneau(Timestamp creneau) {
		this.creneau = creneau;
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
}
