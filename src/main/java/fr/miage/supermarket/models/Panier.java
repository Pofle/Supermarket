package fr.miage.supermarket.models;

import java.util.HashMap;

public class Panier {
	
    private HashMap<String, ProduitPanier> panier = new HashMap<>();

	public void ajouterProduit(ProduitPanier produitPanier) {
		if(produitPanier != null) {
			if(panier.get(produitPanier.getEan()) == null) {
				panier.put(produitPanier.getEan(), produitPanier);
			}
			panier.get(produitPanier.getEan()).ajouterQuantite(produitPanier.getQuantite());
		}
	}

	public HashMap<String, ProduitPanier> getPanier() {
		return panier;
	}

	public void setPanier(HashMap<String, ProduitPanier> panier) {
		this.panier = panier;
	}

	@Override
	public String toString() {
		return "Panier [panier=" + panier + "]";
	}	
}
