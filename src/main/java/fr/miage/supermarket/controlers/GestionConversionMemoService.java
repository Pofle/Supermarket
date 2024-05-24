package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.MemoDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.dao.ShoppingListDAO;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ShoppingList;


public class GestionConversionMemoService extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	private MemoDAO memoDAO;
	
	private ProduitDAO produitDAO;
	
	private ShoppingListDAO shoppingListDAO;
	
	public GestionConversionMemoService() {
		super();
		this.memoDAO = new MemoDAO();
		this.produitDAO = new ProduitDAO();
		this.shoppingListDAO = new ShoppingListDAO();
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String listeIdStr = request.getParameter("listeId");
        if (listeIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing listeId parameter.");
            return;
        }

        int listeId = Integer.parseInt(listeIdStr);

        ShoppingList shoppingList = shoppingListDAO.find(listeId);
        if (shoppingList == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "ShoppingList not found.");
            return;
        }

        memoDAO.deleteMemosByShoppingListId(listeId);
        
        Set<String> addedProducts = new HashSet<>();

        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("selectedProduct_")) {
                String productEan = request.getParameter(paramName);
                if (!addedProducts.contains(productEan)) {
                    Produit produit = produitDAO.find(productEan);
                    if (produit != null) {
                        shoppingList.getProduits().add(produit);
                        addedProducts.add(productEan);
                    }
                }
            }
        }
        shoppingListDAO.update(shoppingList);

        response.sendRedirect("central?type_action=gestion_List");
    }
}

