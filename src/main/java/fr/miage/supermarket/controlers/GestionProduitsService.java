package fr.miage.supermarket.controlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.text.DecimalFormat;

/**
 * Service de gestion de recherche et d'insertion des produits
 * @author EricB
 */
@MultipartConfig
public class GestionProduitsService extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private static final int EXPECTED_COLUMNS = 13;
	
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
	private ProduitDAO produitDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionProduitsService() {
        super();
        this.produitDao = new ProduitDAO();
    }

    /**
	 * Méthode GET permettant de récupérer sous forme de XML des informations sur les produits enregistrés en base de données.
	 * Avec le paramètre libelle renseigné, renvoit l'ensemble des produits détenant ce bout de libelle dans leur libelle.
	 * Sans le paramètre libelle renseigné, renvoit l'ensemble des produits enregistrés en base de données.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author EricB
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Produit> produits = new ArrayList<>();
		
		if(request.getParameter("libelle") != null) {
	        produits = produitDao.getProduitsByLibelle(request.getParameter("libelle"));
		} else {
			produits = produitDao.getAllProduits();
		}
		
		try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Produit.class, ListWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

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
	 * Méthode POST permettant de gérer l'ajout de produits à la base de données.
	 * Paramètre file obligatoire. Ce paramètre est le fichier CSV à importé contenant l'ensemble des produits à enregistrer.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author EricB
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
			produitDao.registerProduits(produits);			
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (ServletException | IOException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Une erreur s'est produite lors de l'import du fichier: " + e.getMessage());
		}
	}
	
	
	
    /**
     * Lit le fichier CSV pour en extraire les produits
     *
     * @param inputStream {@link InputStream} relatif au fichier CSV importé.
     * @return une liste de produits extraits du fichier CSV
     * @throws IOException exception levée si le fichier n'a pas pu être correctement lu
     * @throws CsvValidationException exception levée si la structure du CSV n'est pas respectée.
     * @author EricB
     */
    public List<Produit> readCsvFile(InputStream inputStream) throws IOException {
    	
        List<Produit> produits = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] header = csvReader.readNext(); // Skip the header
            if (header.length != EXPECTED_COLUMNS) {
                throw new IOException("Le fichier est mal formaté. Nombre de colonnes attendu: " + EXPECTED_COLUMNS + ", trouvé: " + header.length);
            }

            String[] data;
            int lineNumber = 1;
            while ((data = csvReader.readNext()) != null) {
                lineNumber++;
                if (data.length != EXPECTED_COLUMNS) {
                    throw new IOException("Le fichier est mal formaté à la ligne " + lineNumber + ". Nombre de colonnes attendu: " + EXPECTED_COLUMNS + ", trouvé: " + data.length);
                }

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

                // Vérification de la valeur du prix
                if (data[9] != null && !data[9].isBlank()) {
                    BigDecimal prix = new BigDecimal(data[9]);
                    if (prix.compareTo(BigDecimal.ZERO) < 0) {
                        throw new IOException("Le prix ne peut pas être inférieur à 0 à la ligne " + lineNumber);
                    }
                    
                    //decimalFormat.format(prix)
                    produit.setPrix(Float.parseFloat(decimalFormat.format(prix).replace(",", ".")));
                }

                // Vérification du poids
                if (data[10] != null && !data[10].isBlank()) {
                	BigDecimal poids = new BigDecimal(data[10]);
                    if (poids.compareTo(BigDecimal.ZERO) < 0) {
                        throw new IOException("Le poids ne peut pas être inférieur à 0 à la ligne " + lineNumber);
                    }
                    produit.setPoids(Float.parseFloat(decimalFormat.format(poids).replace(",", ".")));
                }

                // Vérification du conditionnement
                String conditionnement = data[11].isBlank() ? null : data[11];
                produit.setConditionnement(conditionnement);

                // Vérification de la quantité de conditionnement
                int quantiteConditionnement = 0;
                if (data[12] != null && !data[12].isBlank() && StringUtils.isNumeric(data[12])) {
                    quantiteConditionnement = Integer.parseInt(data[12]);
                    if (quantiteConditionnement < 0) {
                        throw new IOException("La quantité de conditionnement ne peut pas être inférieure à 0 à la ligne " + lineNumber);
                    }
                }
                produit.setQuantiteConditionnement(quantiteConditionnement);

                // Validation des règles de conditionnement et poids
                if (quantiteConditionnement > 0 && conditionnement == null) {
                    throw new IOException("Si une quantité de conditionnement est saisie, un conditionnement doit être saisi à la ligne " + lineNumber);
                }
                if (conditionnement == null && produit.getPoids() == null) {
                    throw new IOException("Si un conditionnement n'est pas saisi, un poids doit être saisi à la ligne " + lineNumber);
                }

                produits.add(produit);
            }
        }
        return produits;
    }
}
