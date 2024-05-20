<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Définion d'un format pour les prix --%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link href="css/accueil.css" rel="stylesheet" type="text/css" />
<title>Accueil</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<div class="search-bar">
		<input type="text" placeholder="Rechercher..." id="search-bar">
	</div>
	<div class="filter-container">
    <label for="categorie-select">Catégorie :</label>
    <select id="categorie-select" multiple>
    </select>

    <label for="rayon-select">Rayon :</label>
    <select id="rayon-select" multiple>
    </select>
</div>

	<div class="article-container" id="article-container"></div>

	<script src="javascript/rechercherProduits.js"></script>
</body>
</html>
