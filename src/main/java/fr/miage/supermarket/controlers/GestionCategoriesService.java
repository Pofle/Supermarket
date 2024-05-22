package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.CategorieDAO;
import fr.miage.supermarket.dao.RayonDAO;
import fr.miage.supermarket.dto.CategorieDTO;
import fr.miage.supermarket.models.Categorie;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.Rayon;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Service de gestion de recherche de catégories de produits
 * @author EricB
 */
public class GestionCategoriesService extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private RayonDAO rayonDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionCategoriesService() {
        super();
        this.rayonDAO = new RayonDAO();
    }

    /**
     * Gère les requêtes HTTP GET en récupérant les catégories d'un rayon dont l'identifiant est passé en paramètre,
     * trie les catégories par libellé, puis en les formate en XML pour les transmettre en réponse.
     *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author EricB
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rayonIdString = request.getParameter("rayonId");
	    Integer rayonId = Integer.parseInt(rayonIdString);
	    Rayon rayon = rayonDAO.findRayonById(rayonId);

	    // Tri nécessaire pour ne pas avoir les catégories qui viennent dans un ordre aléatoire dans le XML
	    // permet de garantir une stabilité à l'IHM sur l'ordre d'affichage
	    List<Categorie> categories = new ArrayList<>(rayon.getCategories());
	    categories.sort(Comparator.comparing(Categorie::getLibelle));
	    
	    StringBuilder xmlResponse = new StringBuilder();
	    xmlResponse.append("<categories>");
	    for (Categorie categorie : categories) {
	      xmlResponse.append("<categorie id='").append(categorie.getId()).append("'>");
	      xmlResponse.append(categorie.getLibelle());
	      xmlResponse.append("</categorie>");
	    }
	    xmlResponse.append("</categories>");

	    response.setContentType("application/xml");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(xmlResponse.toString());
	}
}
