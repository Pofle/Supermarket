package fr.miage.supermarket.models;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LinkUtilisateurCommandeId {

	@Column(name = "ID_COMMANDE")
    private int commandeId;
	
	@Column(name = "ID_UTILISATEUR")
    private int utilisateurId;

	public int getCommandeId() {
		return commandeId;
	}

	public void setCommandeId(int commandeId) {
		this.commandeId = commandeId;
	}

	public int getUtilisateurId() {
		return utilisateurId;
	}

	public void setUtilisateurId(int utilisateurId) {
		this.utilisateurId = utilisateurId;
	}
}
