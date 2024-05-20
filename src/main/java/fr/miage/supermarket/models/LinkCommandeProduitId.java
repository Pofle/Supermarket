package fr.miage.supermarket.models;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LinkCommandeProduitId implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "ID_COMMANDE")
    private Integer commandeId;

    @Column(name = "EAN")
    private String produitId;

    public Integer getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Integer commandeId) {
        this.commandeId = commandeId;
    }

    public String getProduitId() {
        return produitId;
    }

    public void setProduitId(String produitId) {
        this.produitId = produitId;
    }
}