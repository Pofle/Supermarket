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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Servlet qui gère les approvisionnements du stock des produits.
 * Cette servlet gère les requêtes POST pour enregistrer les commandes de réapprovisionnement
 * et mettre à jour les stocks.
 * 
 * @web.servlet GestionApprovisionnementService
 * @web.servlet-mapping /GestionApprovisionnementService
 * 
 * @see HttpServlet
 * @see HttpServletRequest
 * @see HttpServletResponse
 * @see SessionFactory
 * @see Session
 * @see Query
 * @see Produit
 * @see Magasin
 * @see Stock
 * @see Link_Produit_Stock
 * @see Approvisionnement
 * @see HibernateUtil
 * 
 * @author AlexP
 */
public class GestionApprovisionnementService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Gère les requêtes HTTP POST pour traiter les commandes de réapprovisionnement.
     * 
     * @param request  l'objet HttpServletRequest qui contient la requête
     * @param response l'objet HttpServletResponse qui contient la réponse envoyée
     * @throws ServletException si une erreur liée au servlet survient
     * @throws IOException      si une erreur d'entrée/sortie survient
     */
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
                    
                    // Vérifier si la quantité à commander est supérieure à 0
                    if (quantiteCommandee > 0) {
                        String[] parts = paramName.split("_");
                        String ean = parts[1];
                        String magasinNom = parts[2];
                        String dateStockStr = request.getParameter("dateStock");
        
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateStock = sdf.parse(dateStockStr);
        
                        // Calculer la date d'arrivée (date du jour + 5 jours de livraison)
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dateStock);
                        cal.add(Calendar.DATE, 5);
                        Date dateArriveeStock = cal.getTime();
        
                        // Rechercher le produit
                        Produit produit = session.get(Produit.class, ean);
                        
                        // Rechercher le magasin (en s'assurant qu'il n'y a qu'un seul résultat)
                        Query<Magasin> query = session.createQuery("FROM Magasin WHERE nom = :nom", Magasin.class);
                        query.setParameter("nom", magasinNom);
                        List<Magasin> magasins = query.list();
                        if (magasins.size() != 1) {
                            throw new IllegalStateException("Nombre de résultats inattendu pour le magasin: " + magasins.size());
                        }
                        Magasin magasin = magasins.get(0);
        
                        // Rechercher ou créer le stock pour la date d'arrivée
                        Query<Stock> stockQuery = session.createQuery("FROM Stock WHERE dateStock = :dateStock", Stock.class);
                        stockQuery.setParameter("dateStock", dateArriveeStock);
                        Stock stock = stockQuery.uniqueResult();
                        if (stock == null) {
                            stock = new Stock();
                            stock.setDateStock(dateArriveeStock);
                            session.save(stock);
                        }
        
                        // Rechercher le lien produit-stock existant pour la date d'arrivée
                        Query<Link_Produit_Stock> linkQuery = session.createQuery(
                            "FROM Link_Produit_Stock WHERE produit = :produit AND magasin = :magasin AND stock = :stock", 
                            Link_Produit_Stock.class);
                        linkQuery.setParameter("produit", produit);
                        linkQuery.setParameter("magasin", magasin);
                        linkQuery.setParameter("stock", stock);
                        List<Link_Produit_Stock> existingLinks = linkQuery.list();
                        
                        if (!existingLinks.isEmpty()) {
                            // Mettre à jour la quantité existante
                            for (Link_Produit_Stock existingLink : existingLinks) {
                                existingLink.setQuantite(existingLink.getQuantite() + quantiteCommandee);
                                session.update(existingLink);
                            }
                        } else {
                            // Créer un nouveau lien produit-stock
                            Link_Produit_Stock newLink = new Link_Produit_Stock();
                            newLink.setProduit(produit);
                            newLink.setMagasin(magasin);
                            newLink.setStock(stock);
                            newLink.setQuantite((long) quantiteCommandee);
                            session.save(newLink);
                        }
                        
                        // Créer et enregistrer l'approvisionnement
                        Approvisionnement approvisionnement = new Approvisionnement(produit, quantiteCommandee, dateArriveeStock, magasin);
                        session.save(approvisionnement);
                        
                        // Ajouter la quantité commandée aux stocks des jours suivants
                        List<Stock> futureStocks = session.createQuery(
                            "FROM Stock WHERE dateStock > :dateArriveeStock", Stock.class)
                            .setParameter("dateArriveeStock", dateArriveeStock)
                            .list();

                        for (Stock futureStock : futureStocks) {
                            linkQuery = session.createQuery(
                                "FROM Link_Produit_Stock WHERE produit = :produit AND magasin = :magasin AND stock = :stock", 
                                Link_Produit_Stock.class);
                            linkQuery.setParameter("produit", produit);
                            linkQuery.setParameter("magasin", magasin);
                            linkQuery.setParameter("stock", futureStock);
                            existingLinks = linkQuery.list();

                            if (!existingLinks.isEmpty()) {
                                // Mettre à jour la quantité existante pour les stocks futurs
                                for (Link_Produit_Stock existingLink : existingLinks) {
                                    existingLink.setQuantite(existingLink.getQuantite() + quantiteCommandee);
                                    session.update(existingLink);
                                }
                            } else {
                                // Créer un nouveau lien produit-stock pour les futurs stocks
                                Link_Produit_Stock newLink = new Link_Produit_Stock();
                                newLink.setProduit(produit);
                                newLink.setMagasin(magasin);
                                newLink.setStock(futureStock);
                                newLink.setQuantite((long) quantiteCommandee);
                                session.save(newLink);
                            }
                        }
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
        	request.getRequestDispatcher("recapCommandesApprovisionnement").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la commande.");
        }
    }
}
