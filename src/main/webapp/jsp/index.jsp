<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.DecimalFormat"%>
<%-- Définion d'un format pour les prix --%>
<%
	request.setAttribute("decimalFormat", new DecimalFormat("#.00"));
%>
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
