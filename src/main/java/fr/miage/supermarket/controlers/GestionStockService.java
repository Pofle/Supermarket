package fr.miage.supermarket.controlers;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.Produit;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@MultipartConfig
public class GestionStockService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GestionStockService() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProduitDAO produitDAO = new ProduitDAO();
        List<Produit> produits = produitDAO.getProduitsStock15ProchainsJours();
        
        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/jsp/gestionStock.jsp").forward(request, response);
    }
}
