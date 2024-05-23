package fr.miage.supermarket.controlers;

import fr.miage.supermarket.dao.ApprovisionnementDAO;
import fr.miage.supermarket.models.Approvisionnement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GestionRecapApprovisionnementService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ApprovisionnementDAO approvisionnementDAO;

    public GestionRecapApprovisionnementService() {
        this.approvisionnementDAO = new ApprovisionnementDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Object[]> approvisionnements = approvisionnementDAO.getAllApprovisionnements();
        System.out.println("Nombre d'approvisionnements récupérés : " + approvisionnements.size());
        request.setAttribute("approvisionnements", approvisionnements);
        request.getRequestDispatcher("/jsp/recapCommandesApprovisionnement.jsp").forward(request, response);
    }
}
