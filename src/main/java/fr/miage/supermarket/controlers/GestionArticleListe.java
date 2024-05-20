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
import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.ListWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GestionArticleListe
 */
public class GestionArticleListe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionArticleListe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");

         try {
             List<ShoppingList> listes = ShoppingListDAO.getShoppingLists();
             ObjectMapper mapper = new ObjectMapper();
             String json = mapper.writeValueAsString(listes);
             response.getWriter().write(json);
         } catch (Exception e) {
             e.printStackTrace();
             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         }
     }
}

    
	



