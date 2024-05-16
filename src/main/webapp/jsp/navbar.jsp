<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar">
	<div class="navbar-container">
		<div class="navbar-left">
			<a href="central?type_action=accueil" class="navbar-logo">Accueil</a>
			<ul class="navbar-menu">
				<c:if test="${requestScope.categorie == 'GESTIONNAIRE'}">
					<li class="navbar-item"><a href="central?type_action=gestionProduit" class="navbar-link">Gestion des produits</a></li>
					<li class="navbar-item"><a href="central?type_action=gestionStock" class="navbar-link">Gestion du stock</a></li>
					<li class="navbar-item"><a href="central?type_action=gestionCommande" class="navbar-link">Gestion des commandes</a></li>
					<li class="navbar-item"><a href="central?type_action=statistiques" class="navbar-link">Statistiques</a></li>
				</c:if>
				<c:if test="${requestScope.categorie == 'PREPARATEUR'}">
					<li class="navbar-item"><a href="central?type_action=preparationPanier" class="navbar-link">Préparation des paniers</a></li>
				</c:if>
				<c:if test="${requestScope.categorie == 'UTILISATEUR'|| requestScope.categorie == 'VISITEUR'}">
					<li class="navbar-item"><a href="central?type_action=rayons" class="navbar-link">Rayons</a></li>
                    <li class="navbar-item"><a href="central?type_action=promos" class="navbar-link">Promos</a></li>
                    <li class="navbar-item"><a href="central?type_action=gestion_List" class="navbar-link">Listes des courses</a></li>
                    <li class="navbar-item"><a href="central?type_action=panier" class="navbar-link">Panier</a></li>
                    <li class="navbar-item"><a href="central?type_action=connexionInscription" class="navbar-link">Connexion / Inscription</a></li>
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
