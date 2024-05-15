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
			</ul>
		</div>
		<div id="panier-compteur" class="navbar-middle">
		</div>
		<div class="navbar-right">
			<span class="navbar-profile">${categorie}</span>
		</div>
	</div>
</nav>