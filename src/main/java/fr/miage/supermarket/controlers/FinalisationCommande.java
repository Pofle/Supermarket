package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import fr.miage.supermarket.models.Creneau;
import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.utils.HibernateUtil;

@WebServlet("/FinaliserCommande")
public class FinalisationCommande extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idMagasin = Integer.parseInt(request.getParameter("magasin"));
        
        // Récupérer les créneaux du magasin sélectionné
        List<Creneau> creneaux = getCreneaux(idMagasin);
        
        // Envoyer les créneaux à la page JSP
        request.setAttribute("creneaux", creneaux);
        request.getRequestDispatcher("selection.jsp").forward(request, response);
    }

    private List<Creneau> getCreneaux(int idMagasin) {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Creneau> query = session.createQuery("FROM Creneau c WHERE c.magasin.id = :id", Creneau.class);
            query.setParameter("id", idMagasin);
            List<Creneau> creneaux = query.getResultList();
            session.getTransaction().commit();
            return creneaux;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
