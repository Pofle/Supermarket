package fr.miage.supermarket.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LINK_UTILISATEUR_COMMANDE")
public class LinkUtilisateurCommande {

	@EmbeddedId
    private LinkUtilisateurCommandeId id;

	@ManyToOne
	@JoinColumn(name = "ID_UTILISATEUR", insertable=false, updatable=false)
	private Utilisateur utilisateur;
	
	@ManyToOne
    @JoinColumn(name = "ID_COMMANDE", insertable=false, updatable=false)
    private Commande commande;

	public LinkUtilisateurCommande(Utilisateur utilisateur, Commande commande) {
		super();
		this.utilisateur = utilisateur;
		this.commande = commande;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	
}
