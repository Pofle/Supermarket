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
    private LinkUtilisateurCommande id;
	
	@ManyToOne
    @JoinColumn(name = "ID_COMMANDE", insertable=false, updatable=false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "ID_UTILISATEUR", insertable=false, updatable=false)
    private Produit produit;
}
