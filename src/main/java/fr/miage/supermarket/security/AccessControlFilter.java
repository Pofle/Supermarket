package fr.miage.supermarket.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.supermarket.models.CategorieCompte;
import fr.miage.supermarket.models.Utilisateur;

/**
 * Filter pour le contrôle d'accès aux routes
 * @author EricB
 */
public class AccessControlFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;

		HttpSession session = servletRequest.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
		
		String uri = servletRequest.getRequestURI();
		
		String contextPath = servletRequest.getContextPath();
		String pathInfo = uri.substring(contextPath.length());
		
		CategorieCompte categorieCompte = CategorieCompte.VISITEUR;

		if (user != null) {
			categorieCompte = user.getRole();
		}
		if (!checkAccess(pathInfo, categorieCompte)) {
			servletResponse.sendRedirect(servletRequest.getContextPath() + "/");
			return;
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * Vérifie pour une catégorie de compte et un chemin d'url si le compte a accés à l'url demandé.
	 * @param pathInfo l'url à vérifier
	 * @param user le compte cherchant à accéder à l'URL
	 * @return un booleen true = accés autorisé | false = accés refusé
	 * @author EricB
	 */
	private boolean checkAccess(String pathInfo, CategorieCompte user) {
		if (user == null) {
			return false;
		}
		for (Functionnalities functionality : Functionnalities.values()) {
			if (functionality.getChemin().equals(pathInfo)) {
				for (CategorieCompte role : functionality.getRolesAutorises()) {
					if (role.equals(user)) {
						// L'utilisateur a accès à la fonctionnalité
						return true; 
					}
				}
				System.out.println("Accès refusé à "+ user +" pour la route " + pathInfo);
				// L'utilisateur n'a pas le bon rôle pour cette fonctionnalité
				return false; 
			}
		}
		// L'accès est autorisé pour les URLS non associées à une fonctionnalité
		return true; 
	}
}
