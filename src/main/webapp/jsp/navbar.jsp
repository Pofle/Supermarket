<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="central?type_action=accueil">Accueil</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <c:if test="${requestScope.categorie == 'GESTIONNAIRE'}">
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=gestionProduit">Gestion des produits</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=gestionStock">Gestion du stock</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=habitudesConsommation">Statistiques</a>
        </li>
      </c:if>
      <c:if test="${requestScope.categorie == 'PREPARATEUR'}">
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=listePaniers">Préparation des paniers</a>
        </li>
      </c:if>
      <c:if test="${requestScope.categorie == 'UTILISATEUR'}">
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=gestion_List">Listes des courses</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=habitudesConsommation">Mes habitudes</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=nosRayons">Nos rayons</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="CommandeUtilisateur">Commandes en cours</a>
        </li>
      </c:if>
      <c:if test="${requestScope.categorie == 'VISITEUR'}">
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=connexionInscription">Connexion / Inscription</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="central?type_action=nosRayons">Nos rayons</a>
        </li>
      </c:if>
    </ul>
    <ul class="navbar-nav ml-auto">
      <span class="navbar-text"> ${categorie} </span>
      <c:if test="${requestScope.categorie != 'VISITEUR'}">
        <li class="nav-item">
          <a class="nav-link" style="color:#f70e0e;" href="/SupermarketG3/deconnexion" title="Déconnexion">
            <svg class="w-[24px] h-[24px] text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M16 12H4m12 0-4 4m4-4-4-4m3-4h2a3 3 0 0 1 3 3v10a3 3 0 0 1-3 3h-2"/>
            </svg>
          </a>
        </li>
      </c:if>
      <li class="nav-item">
        <a class="nav-link" href="central?type_action=panier" title="Panier">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="32" height="32" color="#ffffff" fill="none">
            <path d="M8 16L16.7201 15.2733C19.4486 15.046 20.0611 14.45 20.3635 11.7289L21 6" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
            <path d="M6 6H22" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
            <circle cx="6" cy="20" r="2" stroke="currentColor" stroke-width="1.5" />
            <circle cx="17" cy="20" r="2" stroke="currentColor" stroke-width="1.5" />
            <path d="M8 20L15 20" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
            <path d="M2 2H2.966C3.91068 2 4.73414 2.62459 4.96326 3.51493L7.93852 15.0765C8.08887 15.6608 7.9602 16.2797 7.58824 16.7616L6.63213 18" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
          </svg>
        </a>
        <span id="panier-compteur" class="navbar-badge"></span>
      </li>
    </ul>
  </div>
</nav>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="javascript/panier.js"></script>