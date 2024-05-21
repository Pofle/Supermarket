package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.Promotion;

public class RecupererProduits extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProduitDAO produitDao;

    public RecupererProduits() {
        super();
        this.produitDao = new ProduitDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ean = request.getParameter("ean");
        if (ean != null) {
            Produit produit = produitDao.getProduitByEan(ean);
            produit.setImageBase64(getServletContext().getRealPath("WEB-INF/images/"));

            Date now = new Date();
            List<Promotion> promotionsDisponibles = new ArrayList<>();
            for (Promotion prom : produit.getPromotions()) {
                if (prom.getDateDebut().before(now) && prom.getDateFin().after(now)) {
                    promotionsDisponibles.add(prom);
                }
            }

            request.setAttribute("categorie", CategorieCompte.GESTIONNAIRE.name());
            request.setAttribute("produit", produit);
            request.setAttribute("promotions", promotionsDisponibles);
            request.getRequestDispatcher("/jsp/detailProduit.jsp").forward(request, response);
        } else {
            // Handle the case where the 'ean' parameter is missing
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter 'ean' is missing");
        }
    }
}
