<%@page import="java.util.*"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp">
	<jsp:param name="title" value="Accueil" />
</jsp:include>
<link href="css/accueil.css" rel="stylesheet" type="text/css" />
<title>Accueil</title>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<h1>Accueil</h1>

	<div class="search-bar">
		<input type="text" placeholder="Rechercher...">
		<button type="button">Rechercher</button>
	</div>
	<div class="article-container" id="article-container"></div>
	<script src="javascript/produits.js"></script>
	
</body>
</html>
