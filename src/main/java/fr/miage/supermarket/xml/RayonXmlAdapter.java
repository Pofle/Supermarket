package fr.miage.supermarket.xml;

import fr.miage.supermarket.models.Rayon;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class RayonXmlAdapter extends XmlAdapter<RayonXml, Rayon> {
	@Override
    public RayonXml marshal(Rayon rayon) throws Exception {
        if (rayon == null) {
            return null;
        }
        RayonXml adapted = new RayonXml();
        adapted.libelle = rayon.getLibelle();
        return adapted;
    }

	@Override
	public Rayon unmarshal(RayonXml v) throws Exception {
		return null;
	}
}
