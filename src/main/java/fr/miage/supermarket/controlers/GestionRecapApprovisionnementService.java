package fr.miage.supermarket.controlers;

import fr.miage.supermarket.models.Approvisionnement;
import fr.miage.supermarket.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/GestionRecapApprovisionnementService")
public class GestionRecapApprovisionnementService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.openSession();

        try {
            List<Approvisionnement> approvisionnements = session.createQuery("FROM Approvisionnement", Approvisionnement.class).list();
            System.out.println("Nombre d'approvisionnements récupérés : " + approvisionnements.size());
            request.setAttribute("approvisionnements", approvisionnements);
            request.getRequestDispatcher("/jsp/recapCommandesApprovisionnement.jsp").forward(request, response);
        } finally {
            session.close();
        }
    }
}

