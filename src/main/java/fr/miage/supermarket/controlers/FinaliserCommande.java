package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Magasin;

public class FinaliserCommande extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String magasinIdStr = request.getParameter("magasin");
        String dateStr = request.getParameter("date");
        String horaireStr = request.getParameter("horaire");

        if (magasinIdStr != null && dateStr != null && horaireStr != null) {
            try {
                int magasinId = Integer.parseInt(magasinIdStr);
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

                // Convertir Date en LocalDate
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now(); // Date actuelle

                Commande commande = new Commande();
                commande.setIdMagasin(magasinId);
                commande.setDateCommande(currentDate); // Date de commande
                commande.setDateRetrait(localDate);
                commande.setHoraireRetrait(horaireStr);

                CommandeDAO commandeDAO = new CommandeDAO();
                commandeDAO.save(commande);

                // Utilisez l'ID du magasin pour récupérer les informations du magasin depuis la base de données
                Magasin magasin = MagasinDAO.getMagasinById(magasinId);

                // Vérifiez si le magasin a été trouvé
                if (magasin != null) {
                    // Ajoutez le nom et l'adresse du magasin aux attributs de la requête
                    request.setAttribute("nomMagasin", magasin.getNom());
                    request.setAttribute("adresseMagasin", magasin.getAdresse());
                } else {
                    request.setAttribute("nomMagasin", "Magasin inconnu");
                    request.setAttribute("adresseMagasin", "Adresse inconnue");
                }

                // Ajout des informations de la commande aux attributs de la requête
                request.setAttribute("dateCommande", commande.getDateCommande());
                request.setAttribute("jourRetrait", commande.getDateRetrait());
                request.setAttribute("horaireRetrait", commande.getHoraireRetrait());

                // page JSP de succès
                request.getRequestDispatcher("jsp/commandeValide.jsp").forward(request, response);

            } catch (NumberFormatException | ParseException e) {
                e.printStackTrace();
                response.sendRedirect("jsp/error.jsp");
            }
        } else {
            response.sendRedirect("jsp/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
