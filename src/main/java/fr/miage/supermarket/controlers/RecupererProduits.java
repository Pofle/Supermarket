package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Servlet implementation class RecupererProduits
 */
public class RecupererProduits extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecupererProduits() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProduitDAO produitDAO = new ProduitDAO();
        List<Produit> produits = produitDAO.getAllProduits();

        try {
            // Configuration du contexte JAXB pour Produit et lister les produits
            JAXBContext jaxbContext = JAXBContext.newInstance(Produit.class, ListWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            // Convertir la liste de produits en XML
            ListWrapper<Produit> wrapper = new ListWrapper<>(produits);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            Writer writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            marshaller.marshal(wrapper, writer);

        } catch (JAXBException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
	}
}
