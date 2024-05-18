<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar">
	<div class="navbar-container">
		<div class="navbar-left">
			<a href="central?type_action=accueil" class="navbar-logo">Accueil</a>
			<ul class="navbar-menu">
				<c:if test="${requestScope.categorie == 'GESTIONNAIRE'}">
					<li class="navbar-item"><a
						href="central?type_action=gestionProduit" class="navbar-link">Gérer
							les produits</a></li>
				</c:if>
				<c:if test="${requestScope.categorie == 'UTILISATEUR'|| requestScope.categorie == 'VISITEUR'}">
					<li class="navbar-item"><a href="central?type_action=gestion_List" class="navbar-link">Listes des courses</a></li>
				</c:if>
				
			</ul>
		</div>
		<span class="navbar-right">
			<span class="navbar-profile">${categorie}</span>
			<div class="navbar-cart-container">
				<a href="/SupermarketG3/afficherPanier">
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="32" height="32" color="#ffffff" fill="none">
    <path d="M8 16L16.7201 15.2733C19.4486 15.046 20.0611 14.45 20.3635 11.7289L21 6" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
    <path d="M6 6H22" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
    <circle cx="6" cy="20" r="2" stroke="currentColor" stroke-width="1.5" />
    <circle cx="17" cy="20" r="2" stroke="currentColor" stroke-width="1.5" />
    <path d="M8 20L15 20" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
    <path d="M2 2H2.966C3.91068 2 4.73414 2.62459 4.96326 3.51493L7.93852 15.0765C8.08887 15.6608 7.9602 16.2797 7.58824 16.7616L6.63213 18" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
</svg>				</a>
				<span id="panier-compteur" class="navbar-badge"></span>
			</div>
		</span>
	</div>
	<script src="javascript/panier.js"></script>
</nav>
