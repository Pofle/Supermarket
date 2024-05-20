package fr.miage.supermarket.xml;

import fr.miage.supermarket.models.Categorie;
import fr.miage.supermarket.models.Produit;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class CategorieXmlAdapter extends XmlAdapter<CategorieXml, Categorie> {
	@Override
    public CategorieXml marshal(Categorie categorie) throws Exception {
        if (categorie == null) {
            return null;
        }
        CategorieXml adapted = new CategorieXml();
        adapted.libelle = categorie.getLibelle();
        adapted.rayon = categorie.getRayon();
        return adapted;
    }

	@Override
	public Categorie unmarshal(CategorieXml v) throws Exception {
		return null;
	}
}
