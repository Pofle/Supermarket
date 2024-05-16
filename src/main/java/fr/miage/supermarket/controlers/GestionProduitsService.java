package fr.miage.supermarket.controlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Service de gestion de recherche et d'insertion des produits
 */
@MultipartConfig
public class GestionProduitsService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionProduitsService() {
        super();
    }

	/**
	 * Renvoit un XML contenant l'intégralité des produits en base.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProduitDAO produitDAO = new ProduitDAO();
        List<Produit> produits = produitDAO.getAllProduits();
		try {
            //Configuration du contexte JAXB pour Produit et lister les produits
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
	/**
	 * Gère l'insertion de produits à partir de l'import CSV
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		try {
			Part filePart = request.getPart("file");
			String fileName = filePart.getSubmittedFileName();

			if (fileName == null || fileName.isEmpty()) {
				throw new ServletException("Aucun fichier n'a été sélectionné");
			}

			if (!fileName.toLowerCase().endsWith(".csv")) {
				throw new ServletException("Le fichier envoyé n'est pas un fichier CSV");
			}
			
			List<Produit> produits = readCsvFile(filePart.getInputStream());
			ProduitDAO produitsDao = new ProduitDAO();
			produitsDao.registerProduits(produits);			
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (ServletException | IOException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Une erreur s'est produite lors de l'import du fichier: " + e.getMessage());
		}
	}
	
	/**
	 * Lit le fichier CSV pour en retirer les produits
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private List<Produit> readCsvFile(InputStream inputStream) throws IOException{
		List<Produit> produits = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			reader.readLine(); // On fait un premier readLine pour skip le header
			while((line = reader.readLine()) != null) {
				String data[] = line.split(",");
				if(data.length >= 11) {
					Produit produit = new Produit();
					produit.setEan(data[0]);
					produit.setLibelle(data[1]);
					produit.setDescriptionCourte(data[2]);
					produit.setDescription(data[3]);
					produit.setNutriscore(data[4]);
					produit.setLabel(data[5]);
					produit.setMarque(data[6]);
					produit.setRepertoireImage(data[7]);
					produit.setRepertoireVignette(data[8]);
					produit.setPrix(Float.valueOf(data[9]));
					produit.setPoids(data[10].isBlank() ? null : Float.valueOf(data[10]));
					
					if(data.length == 12) {
						produit.setConditionnement(data[11]);
					}
					
					produits.add(produit);
				} else {
					throw new IOException("Le fichier est mal formaté");
				}
			}
		}
		return produits;
	}
}
