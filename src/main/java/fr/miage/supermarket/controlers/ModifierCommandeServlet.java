package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.models.Commande;

@WebServlet("/modifierCommande")
public class ModifierCommandeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CommandeDAO commandeDAO;

    public ModifierCommandeServlet() {
        this.commandeDAO = new CommandeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("modifier".equals(action)) {
            int idCommande = Integer.parseInt(request.getParameter("idCommande"));
            String magasinIdStr = request.getParameter("magasinId");
            String dateRetraitStr = request.getParameter("dateRetrait");
            String horaireRetrait = request.getParameter("horaireRetrait");
            boolean statut = Boolean.parseBoolean(request.getParameter("statut"));

            // Récupérer la commande à partir de l'ID
            Commande commande = commandeDAO.getCommandeById(idCommande);
            if (commande != null) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateRetrait = sdf.parse(dateRetraitStr);

                    int magasinId = Integer.parseInt(magasinIdStr);

                    // Mettre à jour les informations de la commande
                    commande.setIdMagasin(magasinId);
                    commande.setDateRetrait(dateRetrait.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    commande.setHoraireRetrait(horaireRetrait);
                    commande.setStatut(statut);

                    // Enregistrer les modifications dans la base de données
                    commandeDAO.mettreAJourCommande(commande);

                    // Redirection après la mise à jour
                    response.sendRedirect("afficherCommande");
                } catch (ParseException e) {
                    e.printStackTrace();
                    response.sendRedirect("jsp/error.jsp");
                }
            } else {
                response.sendRedirect("jsp/error.jsp");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
