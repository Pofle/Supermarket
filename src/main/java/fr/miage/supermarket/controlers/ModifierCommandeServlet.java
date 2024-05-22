package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.MagasinDAO;
import fr.miage.supermarket.models.Magasin;

@WebServlet("/modifierCommande")
public class ModifierCommandeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CommandeDAO commandeDAO;
    private MagasinDAO magasinDAO;

    public ModifierCommandeServlet() {
        super();
        this.commandeDAO = new CommandeDAO();
        this.magasinDAO = new MagasinDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("modifier".equals(action)) {
            String idCommande = request.getParameter("idCommande");
            String magasinId = request.getParameter("magasinId");
            String dateRetrait = request.getParameter("dateRetrait");
            String horaireRetrait = request.getParameter("horaireRetrait");
            
            Magasin mag = magasinDAO.getMagasinById(Integer.parseInt(magasinId));
            System.out.println(dateRetrait);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            System.out.println(magasinId);
            try {
                LocalDate dateRetraitFormatted = LocalDate.parse(dateRetrait + "T" + horaireRetrait + ":00", formatter);
                commandeDAO.updateCommande(idCommande, mag, dateRetraitFormatted, horaireRetrait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/CommandeUtilisateur");
    }

}
