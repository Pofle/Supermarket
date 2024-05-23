package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.MemoDAO;
import fr.miage.supermarket.models.Produit;

/**
 * Servlet implementation class ServletConversionMemoProduit
 */
public class ServletConversionMemoProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String XML_FILE_PATH = "C:/Users/Pauline/Cours/Projet/produits_memo.xml";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConversionMemoProduit() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Récupérer les mémos à convertir depuis les paramètres du formulaire
        List<String> memosList = new ArrayList<>();
        //Log de controle
        request.getParameterMap().forEach((key, values) -> {
            if (key.startsWith("libelle_")) {
                System.out.println("Received memo parameter: " + key + " = " + values[0]);
                memosList.add(values[0]);
            }
        });
        //Fin

     // Récupérer tous les paramètres dont les noms commencent par "libelle_"
        request.getParameterMap().forEach((key, values) -> {
            if (key.startsWith("libelle_")) {
                memosList.add(values[0]);
            }
        });
        if (memosList.isEmpty()) {
            System.err.println("No memos received for conversion.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No memos received for conversion.");
            return;
        }
        System.out.println("Memos reçus pour conversion: " + memosList);
        
        List<Produit> produits = MemoDAO.rechercherProduitsPourMemos(memosList);


        // Définir le type de contenu de la réponse comme XML
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");

        // Construire le contenu XML pour les produits
        String xmlResponse = construireXMLResponse(produits);

        // Envoyer la réponse XML
        PrintWriter out = response.getWriter();
        out.println(xmlResponse);
        
     // Enregistrer la réponse XML dans un fichier
        enregistrerXMLDansFichier(xmlResponse, XML_FILE_PATH);
        System.out.println("XML_MemoProduit generated");
    }

    private String construireXMLResponse(List<Produit> produits) {
        // Construire le contenu XML en utilisant une librairie comme JAXB ou DOM
        // Pour cet exemple, construisons simplement une chaîne XML manuellement
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<produits>");

        for (Produit produit : produits) {
            xmlBuilder.append("<produit>");
            xmlBuilder.append("<libelle>").append(produit.getLibelle()).append("</libelle>");
            xmlBuilder.append("<marque>").append(produit.getMarque()).append("</marque>");
            // Ajoutez d'autres éléments si nécessaire
            xmlBuilder.append("</produit>");
        }

        xmlBuilder.append("</produits>");
        return xmlBuilder.toString();
    }
    
    private void enregistrerXMLDansFichier(String xmlContent, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print(xmlContent);
        }
    }
}
