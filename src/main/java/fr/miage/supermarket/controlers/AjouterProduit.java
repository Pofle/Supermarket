package fr.miage.supermarket.controlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.Produit;

/**
 * Servlet implementation class AjouterProduit
 */
@MultipartConfig
public class AjouterProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjouterProduit() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			
			System.out.println(produits.size());
			
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (ServletException | IOException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Une erreur s'est produite lors de l'import du fichier: " + e.getMessage());
		}
	}

	private List<Produit> readCsvFile(InputStream inputStream) throws IOException{
		List<Produit> produits = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while((line = reader.readLine()) != null) {
				String data[] = line.split(",");
				if(data.length == 10) {
					Produit produit = new Produit();
					produit.setEan(data[0]);
					produit.setDescriptionCourte(data[2]);
					produit.setDescription(data[3]);
					produit.setNutriscore(data[4]);
					produit.setLabel(data[5]);
					produit.setMarque(data[7]);
					produit.setRepertoireImage(data[8]);
					produit.setRepertoireVignette(data[9]);
					produits.add(produit);
				} else {
					throw new IOException("Le fichier est mal formaté");
				}
			}
		}
		return produits;
	}
	
}
