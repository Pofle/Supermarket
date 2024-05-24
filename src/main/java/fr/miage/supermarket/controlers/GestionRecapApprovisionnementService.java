package fr.miage.supermarket.controlers;

import fr.miage.supermarket.dao.ApprovisionnementDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet affichant le récapitulatif des commandes d'approvisionnements en cours
 * Cette servlet gère les requêtes POST pour récupérer la liste des commandes d'approvisionnements
 * et les afficher dans une page JSP.
 * 
 * @see HttpServlet
 * @see HttpServletRequest
 * @see HttpServletResponse
 * @see ApprovisionnementDAO
 * 
 * @author AlexP
 */
public class GestionRecapApprovisionnementService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ApprovisionnementDAO approvisionnementDAO;

    /**
     * Constructeur de la servlet GestionRecapApprovisionnementService.
     * Initialise le DAO d'approvisionnement.
     */
    public GestionRecapApprovisionnementService() {
        this.approvisionnementDAO = new ApprovisionnementDAO();
    }

    /**
     * Gère les requêtes HTTP POST pour récupérer et afficher le récapitulatif des approvisionnements.
     * 
     * @param request  l'objet HttpServletRequest qui contient la requête du client
     * @param response l'objet HttpServletResponse qui contient la réponse envoyée au client
     * @throws ServletException si une erreur liée au servlet survient
     * @throws IOException      si une erreur d'entrée/sortie survient
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Object[]> approvisionnements = approvisionnementDAO.getAllApprovisionnements();
        request.setAttribute("approvisionnements", approvisionnements);
        request.getRequestDispatcher("/jsp/recapCommandesApprovisionnement.jsp").forward(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Object[]> approvisionnements = approvisionnementDAO.getAllApprovisionnements();
        request.setAttribute("approvisionnements", approvisionnements);
        request.getRequestDispatcher("/jsp/recapCommandesApprovisionnement.jsp").forward(request, response);
    }
}
