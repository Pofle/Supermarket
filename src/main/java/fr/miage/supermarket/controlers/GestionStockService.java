package fr.miage.supermarket.controlers;

import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.dao.StockDAO;
import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.utils.DatesUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servlet qui gère les stocks produits des magasins.
 * Cette servlet gère les requêtes GET pour afficher les informations de stock
 * pour une série de dates
 * 
 * @see HttpServlet
 * @see HttpServletRequest
 * @see HttpServletResponse
 * @see Magasin
 * @see MagasinDAO
 * @see StockDAO
 * @see DatesUtils
 * 
 * @author AlexP
 */
public class GestionStockService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de la servlet GestionStockService.
     * Appelle le constructeur de la classe parente HttpServlet.
     */
    public GestionStockService() {
        super();
    }
    
    /**
     * Gère les requêtes HTTP GET pour récupérer et afficher les informations de stock.
     * 
     * @param request  l'objet HttpServletRequest qui contient la requête du client
     * @param response l'objet HttpServletResponse qui contient la réponse envoyée au client
     * @throws ServletException si une erreur liée au servlet survient
     * @throws IOException      si une erreur d'entrée/sortie survient
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date(); // Instanciation de la date du jour
        String todayDate = dateFormat.format(today);
        List<Date> serieDates = DatesUtils.getSerieDates(today, 15); // Génération des 15 dates suivant la date du jour
        
        StockDAO stockDAO = new StockDAO();
        List<Object[]> produitsStock = stockDAO.getProduitsStockSerieDates(serieDates);
        
        // Extraction des dates de stock
        List<String> datesStock = new ArrayList<>();
        for (Object[] produitStock : produitsStock) {
            String dateStock = produitStock[3].toString(); // La 4ème colonne contient la date de stock
            if (!datesStock.contains(dateStock)) {
                datesStock.add(dateStock);
            }
        }
        
        // Récupération des magasins
        List<Magasin> magasins = MagasinDAO.getAllMagasins();
        
        request.setAttribute("produitsStock", produitsStock);
        request.setAttribute("datesStock", datesStock); // Ajout des dates de stock à la requête
        request.setAttribute("magasins", magasins); // Ajout des magasins à la requête
        request.setAttribute("todayDate", todayDate); // Ajout de la date du jour à la requête
        request.getRequestDispatcher("/jsp/gestionStock.jsp").forward(request, response);
    }
}
