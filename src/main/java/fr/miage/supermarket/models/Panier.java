package fr.miage.supermarket.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

public class Panier {
    private List<HashMap<String, String>> panier = new ArrayList<>();

	public void ajouterProduit(HashMap<String, String> produit) {
		panier.add(produit);
	}

}
