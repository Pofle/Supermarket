package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.CategorieDAO;
import fr.miage.supermarket.dto.CategorieDTO;
import fr.miage.supermarket.models.Categorie;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Servlet implementation class GestionCategoriesService
 */
public class GestionCategoriesService extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private CategorieDAO categorieDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionCategoriesService() {
        super();
        this.categorieDAO = new CategorieDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Categorie> categories = categorieDAO.getAllCategorie();
		
		List<CategorieDTO> categorieDTOs = new ArrayList<>();

	    for (Categorie categorie : categories) {
	        CategorieDTO categorieDTO = new CategorieDTO();
	        categorieDTO.setLibelle(categorie.getLibelle());
	        categorieDTOs.add(categorieDTO);
	    }
		
		try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CategorieDTO.class, ListWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            ListWrapper<CategorieDTO> wrapper = new ListWrapper<>(categorieDTOs);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            Writer writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            marshaller.marshal(wrapper, writer);
        } catch (JAXBException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
	}
}
