package fr.miage.supermarket.controlers;

import fr.miage.supermarket.models.Approvisionnement;
import fr.miage.supermarket.models.Link_Produit_Stock;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.Stock;
import fr.miage.supermarket.models.Magasin;
import fr.miage.supermarket.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

@WebServlet("/GestionApprovisionnementService")
public class GestionApprovisionnementService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        boolean success = true;

        try {
            Enumeration<String> parameterNames = request.getParameterNames();

            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                
                if (paramName.startsWith("quantiteCommandee_")) {
                    int quantiteCommandee = Integer.parseInt(request.getParameter(paramName));
                    
                    // Vérifier si la quantité à commander est supérieure à zéro
                    if (quantiteCommandee > 0) {
                        String[] parts = paramName.split("_");
                        String ean = parts[1];
                        String magasinNom = parts[2];
                        String dateStockStr = request.getParameter("dateStock");
        
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateStock = sdf.parse(dateStockStr);
        
                        // Calculer la date d'arrivée (dateStock + 5 jours)
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dateStock);
                        cal.add(Calendar.DATE, 5);
                        Date dateArriveeStock = cal.getTime();
        
                        // Rechercher le produit et le magasin
                        Produit produit = session.get(Produit.class, ean);
                        Query<Magasin> query = session.createQuery("FROM Magasin WHERE nom = :nom", Magasin.class);
                        query.setParameter("nom", magasinNom);
                        Magasin magasin = query.uniqueResult();
        
                        // Rechercher ou créer le stock
                        Query<Stock> stockQuery = session.createQuery("FROM Stock WHERE dateStock = :dateStock", Stock.class);
                        stockQuery.setParameter("dateStock", dateArriveeStock);
                        Stock stock = stockQuery.uniqueResult();
                        if (stock == null) {
                            stock = new Stock();
                            stock.setDateStock(dateArriveeStock);
                            session.save(stock);
                        }
        
                        // Rechercher le lien produit-stock existant
                        Query<Link_Produit_Stock> linkQuery = session.createQuery(
                            "FROM Link_Produit_Stock WHERE produit = :produit AND magasin = :magasin AND stock = :stock", 
                            Link_Produit_Stock.class);
                        linkQuery.setParameter("produit", produit);
                        linkQuery.setParameter("magasin", magasin);
                        linkQuery.setParameter("stock", stock);
                        Link_Produit_Stock linkProduitStock = linkQuery.uniqueResult();
        
                        if (linkProduitStock != null) {
                            // Mettre à jour la quantité existante
                            linkProduitStock.setQuantite(linkProduitStock.getQuantite() + quantiteCommandee);
                            session.update(linkProduitStock);
                        } else {
                            // Créer un nouveau lien produit-stock
                            linkProduitStock = new Link_Produit_Stock();
                            linkProduitStock.setProduit(produit);
                            linkProduitStock.setMagasin(magasin);
                            linkProduitStock.setStock(stock);
                            linkProduitStock.setQuantite((long) quantiteCommandee);
                            session.save(linkProduitStock);
                        }
                        
                        // Créer et enregistrer l'approvisionnement
                        Approvisionnement approvisionnement = new Approvisionnement(produit, quantiteCommandee, dateArriveeStock, magasin);
                        session.save(approvisionnement);
                    } else {
                        // Si la quantité à commander est égale à zéro, passer à l'itération suivante
                        continue;
                    }
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            success = false;
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (success) {
            response.sendRedirect("/jsp/recapCommandesApprovisionnement.jsp");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la commande.");
        }
    }
}
