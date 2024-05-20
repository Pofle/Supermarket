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

import fr.miage.supermarket.dao.RayonDAO;
import fr.miage.supermarket.dto.CategorieDTO;
import fr.miage.supermarket.dto.RayonDTO;
import fr.miage.supermarket.models.Categorie;
import fr.miage.supermarket.models.Rayon;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Servlet implementation class GestionRayonsService
 */
public class GestionRayonsService extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private RayonDAO rayonDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionRayonsService() {
        super();
        this.rayonDAO = new RayonDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Rayon> rayons = rayonDAO.getAllRayons();
		
		List<RayonDTO> rayonDTOs = new ArrayList<>();

	    for (Rayon rayon : rayons) {
	    	RayonDTO rayonDTO = new RayonDTO();
	    	rayonDTO.setLibelle(rayon.getLibelle());
	    	rayonDTOs.add(rayonDTO);
	    }
		
		try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RayonDTO.class, ListWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            ListWrapper<RayonDTO> wrapper = new ListWrapper<>(rayonDTOs);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            Writer writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            marshaller.marshal(wrapper, writer);
        } catch (JAXBException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
	}
}
