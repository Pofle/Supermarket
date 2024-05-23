package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.models.Panier;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ProduitPanier;
import fr.miage.supermarket.models.StatutCommande;
import fr.miage.supermarket.models.Utilisateur;

/**
 * Servlet implementation class GestionChoixPanierServlet
 */
public class GestionChoixPanierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CommandeDAO commandeDAO;
	
	private ProduitDAO produitDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionChoixPanierServlet() {
        super();
        this.commandeDAO = new CommandeDAO();
        this.produitDAO = new ProduitDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String choice = request.getParameter("choice");
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        
        if ("garder".equals(choice)) {
        	enregistrerPanierToPanierNonFinalise(utilisateur, (Panier) session.getAttribute("panier"));
        } else if ("remplacer".equals(choice)) {
        	session.removeAttribute("panier");
    		Panier panier = chargerPanierNonFinalise(utilisateur);
    		session.setAttribute("panier", panier);
        }
    }
    
    /**
	 * Charge le panier non finalisé d'un utilisateur à partir de la base de
	 * données, afin de remplir le panier en session
	 *
	 * @param utilisateur L'utilisateur dont on souhaite charger le panier non
	 *                    finalisé.
	 * @return Le panier non finalisé de l'utilisateur s'il en a un, sinon null.
	 * @author EricB
	 */
	private Panier chargerPanierNonFinalise(Utilisateur utilisateur) {
		Commande commande = commandeDAO.getCommandeNonFinalisee(utilisateur.getId());
		if (commande != null) {
			Panier panier = new Panier();
			for (LinkCommandeProduit link : commande.getProduits()) {
				ProduitPanier produitPanier = new ProduitPanier(link.getProduit().getLibelle(),
						link.getProduit().getEan(), link.getQuantite(), link.getProduit().getPrix(), null,
						link.getProduit().getConditionnement(), link.getProduit().getPoids(),
						link.getProduit().getRepertoireImage());
				panier.ajouterProduit(produitPanier);
			}
			return panier;
		}
		return null;
	}
    
	/**
	 * Enregistre le panier d'un utilisateur en tant que commande non finalisée dans
	 * la base de données. (écrase le précédent panier non finalisé)
	 *
	 * @param utilisateur L'utilisateur pour lequel la commande non finalisée est
	 *                    enregistrée.
	 * @param panier      Le panier à enregistrer en tant que commande non
	 *                    finalisée.
	 * @author EricB
	 */
	private void enregistrerPanierToPanierNonFinalise(Utilisateur utilisateur, Panier panier) {
		// On supprime la précédente commande NON_VALIDE
		Commande commandeToDelete = commandeDAO.getCommandeNonFinalisee(utilisateur.getId());
		commandeDAO.supprimerCommande(commandeToDelete);
		
		Commande commande = new Commande();
		commande.setUtilisateur(utilisateur);
		commande.setStatut(StatutCommande.NON_VALIDE);
		commande = commandeDAO.creerCommande(commande);

		for (ProduitPanier produitPanier : panier.getPanier().values()) {
			Produit produit = produitDAO.getProduitByEan(produitPanier.getEan());
			LinkCommandeProduit newLink = new LinkCommandeProduit(commande, produit, produitPanier.getQuantite());
			commandeDAO.updateLinkCommandeProduit(newLink);
		}
	}
}
