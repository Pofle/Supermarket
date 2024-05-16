package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.models.Magasin;

public class MagasinService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupération de la liste des magasins depuis la base de données
            List<Magasin> magasins = MagasinDAO.getAllMagasins();
            
            // Ajout de la liste des magasins à l'attribut de la requête
            request.setAttribute("magasins", magasins);
            
            // Redirection vers la page JSP
            request.getRequestDispatcher("/magasin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
