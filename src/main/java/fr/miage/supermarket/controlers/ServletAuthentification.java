package fr.miage.supermarket.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.dao.UtilisateurDAO;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.LinkCommandeProduit;
import fr.miage.supermarket.models.Panier;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ProduitPanier;
import fr.miage.supermarket.models.StatutCommande;
import fr.miage.supermarket.models.Utilisateur;

public class ServletAuthentification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CommandeDAO commandeDAO;
	private ProduitDAO produitDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAuthentification() {
		super();
		this.commandeDAO = new CommandeDAO();
		this.produitDAO = new ProduitDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mail = request.getParameter("mail").trim();
		String password = request.getParameter("password").trim();

		// Création d'une instance d'utilisateur pour interroger la BDD, on stocke en
		// local un utilisateur à partir de son mail
		UtilisateurDAO user = new UtilisateurDAO();
		Utilisateur userConnecting = user.getUtilisateurByMail(mail);
		String mdpUser = "";
		String hashedPassword = "";
		if (userConnecting != null) {
			mdpUser = userConnecting.getMotdepasse();
			hashedPassword = user.hacherMotdePasse(password);
		}

		// Si valide, on redirige vers une page de confirmation, et on enregistre
		// l'utilisateur en session HTTP
        if (mdpUser.equals(hashedPassword) && userConnecting != null && userConnecting.getMail().equals(mail)) {
			System.out.println("Connexion réussie");
			request.setAttribute("message", "Authentification réussie ! Bienvenue, " + mail + ".");
			HttpSession session = request.getSession();
			session.setAttribute("utilisateur", userConnecting);

			Panier panier = chargerPanierNonFinalise(userConnecting);
			if (panier != null) {
				session.setAttribute("panier", panier);
			} else {
				if (session.getAttribute("panier") != null) {
					enregistrerPanierToPanierNonFinalise(userConnecting, (Panier) session.getAttribute("panier"));
				}
			}

			request.getRequestDispatcher("/jsp/confirmLogin.jsp").forward(request, response);
		} else {
			// Si la connexion n'est pas valide, on explique pourquoi et on propose à
			// nouveau à l'utilisateur de se connecter
            if (userConnecting == null || !userConnecting.getMail().equals(mail)) {
				System.out.println("Il n'existe pas d'utilisateur avec ce mail");
				request.setAttribute("message", "Il n'existe pas d'utilisateur avec ce mail");
			} else {
				System.out.println("Mot de passe incorrect");
				request.setAttribute("message", "Mot de passe incorrect");
			}
			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}

	}

	/**
	 * Charge le panier non finalisé d'un utilisateur à partir de la base de
	 * données.
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
	 * la base de données.
	 *
	 * @param utilisateur L'utilisateur pour lequel la commande non finalisée est
	 *                    enregistrée.
	 * @param panier      Le panier à enregistrer en tant que commande non
	 *                    finalisée.
	 * @author EricB
	 */
	private void enregistrerPanierToPanierNonFinalise(Utilisateur utilisateur, Panier panier) {
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
