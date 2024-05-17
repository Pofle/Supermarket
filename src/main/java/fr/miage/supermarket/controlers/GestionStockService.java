package fr.miage.supermarket.controlers;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.utils.DatesUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/GestionStockService")
public class GestionStockService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GestionStockService() {
        super();
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ProduitDAO produitDAO = new ProduitDAO();
//        List<Produit> produits = produitDAO.getProduitsStock15ProchainsJours();
//        
//        request.setAttribute("produits", produits);
//        request.getRequestDispatcher("/jsp/gestionStock.jsp").forward(request, response);
//    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date0 = null;
        try {
            date0 = dateFormat.parse("2024-05-17"); // Date de départ
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        List<Date> serieDates = DatesUtils.getSerieDates(date0, 15); // Génère 15 jours de dates
        
        ProduitDAO produitDAO = new ProduitDAO();
        List<Object[]> produitsStock = produitDAO.getProduitsStockSerieDates(serieDates);
        
        request.setAttribute("produitsStock", produitsStock);
        request.getRequestDispatcher("/jsp/gestionStock.jsp").forward(request, response);
    }
}
