package fr.miage.supermarket.utils;

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
		
		request.setAttribute("categorie", categorieCompte);
		
		chain.doFilter(servletRequest, servletResponse);
	}
}