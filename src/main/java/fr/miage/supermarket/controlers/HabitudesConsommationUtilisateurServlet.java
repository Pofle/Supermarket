package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.dao.CommandeDAO;
import fr.miage.supermarket.dao.ProduitDAO;
import fr.miage.supermarket.dao.StatistiquesDAO;
import fr.miage.supermarket.models.Commande;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.Utilisateur;

/**
 * Servlet implementation class HabitudesConsommationUtilisateurServlet
 */
public class HabitudesConsommationUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private StatistiquesDAO statistiquesDAO;
	
	
	public HabitudesConsommationUtilisateurServlet() {
		super();
		this.statistiquesDAO = new StatistiquesDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur == null) {
            response.sendRedirect("connexion");
            return;
        }

        Map<String, Object> statistiques = statistiquesDAO.getStatistiquesConsommation(utilisateur);
        request.setAttribute("statistiques", statistiques);
        
        CommandeDAO commandeDAO = new CommandeDAO();
        ProduitDAO produitDAO = new ProduitDAO();
        List<Commande> listeCommandeUser = commandeDAO.getCommandeUtilisateur(utilisateur);
        HashMap<Produit, Integer> map = new HashMap<Produit, Integer>();
        
        //On vérifie que l'utilisateur a passé au moins une commande, si c'est le cas on en parcourt la liste (dans le cas du test, deux fois car deux commandes)
        if (listeCommandeUser != null) {			
        	for (int i = 0; i < listeCommandeUser.size(); i++) {
        		Commande cmde = listeCommandeUser.get(i);
        		List<Produit> listeProd = produitDAO.getProduitsParIdCommande(cmde.getId_commande());
        		//On récupère la liste des produits commandés dans la commande analysée
        		for (int j = 0; j < listeProd.size(); j++) {
        			//Affectation dans une map du produit et sa quantitée commandée (ou incrémentation)
        			int montant_ajoute = produitDAO.getQuantiteCommandee(listeProd.get(j));
        			
        			boolean prodExistantDansMap = false;
        			if (listeProd.size() > 0) {
	        			//Le tri se fait sur la signature de l'objet produit, or deux mêmes produits peuvent avoir deux signatures différentes car dans plusieurs commandes
	        			//En train de faire la verif sur l'EAN de chaque element de la liste
	        			for (Map.Entry<Produit, Integer> entry : map.entrySet()) {
							if (entry.getKey().getEan().equals(listeProd.get(j).getEan())) {
								prodExistantDansMap = true;
							}
						}
        			}
        			if (!prodExistantDansMap) {
        				map.put(listeProd.get(j), montant_ajoute);
					} 
				}
        		
        	}
        	
			
        	
        	List<Map.Entry<Produit, Integer>> entryList = new ArrayList<>(map.entrySet());
            entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
            Map<Produit, Integer> sortedMap = entryList.stream().collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));
            request.setAttribute("mapTop10", sortedMap);
		}
        request.getRequestDispatcher("/jsp/statistiquesConsommation.jsp").forward(request, response);
	}
}
