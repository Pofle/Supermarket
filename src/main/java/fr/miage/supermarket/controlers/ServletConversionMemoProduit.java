package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConversionMemoProduit() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> memosList = new ArrayList<>();
        request.getParameterMap().forEach((key, values) -> {
            if (key.startsWith("libelle_")) {
                memosList.add(values[0]);
            }
        });

        if (memosList.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No memos received for conversion.");
            return;
        }
        
        System.out.println("Memos reçus pour conversion: " + memosList);
        HashMap<String, List<Produit>> mapListProduits = new HashMap<>();
        for(String memo: memosList) {
        	List<Produit> produits = MemoDAO.rechercherProduitsPourMemo(memo);
        	mapListProduits.put(memo, produits);
        }
        
        request.setAttribute("produitsMemos", mapListProduits);
        request.setAttribute("listeId", request.getParameter("listeId"));
        
        request.getRequestDispatcher("/jsp/conversionEnProduits.jsp").forward(request, response);
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
            xmlBuilder.append("<descriptionCourte>").append(produit.getDescriptionCourte()).append("</descriptionCourte>");
            xmlBuilder.append("<nutriscore>").append(produit.getNutriscore()).append("</nutriscore>");
            xmlBuilder.append("<label>").append(produit.getLabel()).append("</label>");
            xmlBuilder.append("<prix>").append(produit.getPrix()).append("</prix>");
            xmlBuilder.append("<imageLocation>").append(produit.getRepertoireImage()).append("</imageLocation>");
            xmlBuilder.append("</produit>");
        }
        xmlBuilder.append("</produits>");
        return xmlBuilder.toString();
    }
}
