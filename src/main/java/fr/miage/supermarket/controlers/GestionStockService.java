package fr.miage.supermarket.controlers;

import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.dao.StockDAO;
import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.utils.DatesUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servlet d'implementation de la classe GestionStockService
 * Cette classe sert à gérer les requêtes pour la gestion des stocks des produits.
 * Génère une série de dates, récupère les produits en stock pour ces dates et les transmet à la vue (JSP) pour affichage.
 * @author AlexP
 */
@WebServlet("/GestionStockService")
public class GestionStockService extends HttpServlet {
    private static final long serialVersionUID = 1L;

     /**
     * Constructeur par défaut.
     */
    public GestionStockService() {
        super();
    }
    
    /**
     * Cette méthode génère une série de 15 dates à partir de la date actuelle,
     * récupère les informations de stock pour ces dates via le DAO (Data Access Object),
     * extrait les dates de stock distinctes, et transmet les données à la JSP pour affichage.
     * @param request  l'objet HttpServletRequest contenant la requête
     * @param response l'objet HttpServletResponse contenant la réponse du servlet
     * @throws ServletException si une erreur de type servlet survient
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date(); // Instanciation de la date du jour
        String todayDate = dateFormat.format(today);
        //Date date0 = new Date(); // Initialise la date de départ
        List<Date> serieDates = DatesUtils.getSerieDates(today, 15); // Génération des 15 dates suivant la date de départ
        
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
