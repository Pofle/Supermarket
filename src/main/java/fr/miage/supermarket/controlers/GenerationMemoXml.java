package fr.miage.supermarket.controlers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import fr.miage.supermarket.dao.LinkListeProduitDAO;
import fr.miage.supermarket.dao.MemoDAO;
import fr.miage.supermarket.models.LinkListeProduit;
import fr.miage.supermarket.models.Memo;

/**
 * Servlet implementation class GenerationMemoXml
 */
public class GenerationMemoXml extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerationMemoXml() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Methode doGet qui récupère les memos d'une liste et les convertit en Xml
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
        Integer listeId = getIntegerParameter(request, "listeId");
        //Log de controle
        System.out.println("ListeId recevid for memo xml conversion is : "+listeId);
        
        
        try {
            List<Memo> memos = MemoDAO.getMemoByListId(listeId);

         // Création du contenu XML
            StringBuilder xmlContent = new StringBuilder();
            xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlContent.append("<memos>");

            for (Memo memo : memos) {
            	xmlContent.append("<memo>");
            	xmlContent.append("<id_memo>").append(memo.getId()).append("</id_memo>");
                xmlContent.append("<libelle>").append(memo.getLibelle()).append("</libelle>");
                xmlContent.append("</memo>");                
            }
            xmlContent.append("</memos>");

            // Écriture du contenu XML dans la réponse
            out.println(xmlContent.toString());

            // Log de contrôle de la génération du XML
            System.out.println("XML_MemoContent response generated");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            out.close();
        }
	}

	/**
	 * Method generique pourconvertir un parametre type INT en STRING
	 * @param request L'objet HttpServletRequest contenant la requête
	 * @param stringParam parametre string qui sera converti en INT
	 * @return
	 * @author Pauline
	 */
	private Integer getIntegerParameter(HttpServletRequest request, String stringParam) {
	    String paramValue = request.getParameter(stringParam);
	    if (paramValue != null && !paramValue.isEmpty()) {
	        try {
	            return Integer.parseInt(paramValue);
	        } catch (NumberFormatException e) {
	            
	        }
	    }
	    return null; 
	}

}
