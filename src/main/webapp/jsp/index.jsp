<%@page import="java.util.*"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.text.DecimalFormat"%>
<%-- Définion d'un format pour les prix --%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/jsp/header.jsp" />
		<link href="css/accueil.css" rel="stylesheet" type="text/css" />
		<title>Accueil</title>
	</head>
	<body>
		<jsp:include page="/jsp/navbar.jsp" />
		<div class="search-bar" >
			<input type="text" placeholder="Rechercher..." id="search-bar">
		</div>
		<div class="article-container" id="article-container">
		</div>
		<script src="javascript/rechercherProduits.js"></script>
	</body>
</html>
