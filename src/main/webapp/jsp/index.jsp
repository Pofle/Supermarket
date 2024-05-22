<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Définion d'un format pour les prix --%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link href="css/accueil.css" rel="stylesheet" type="text/css" />
<title>Accueil</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<div class="container">
    <div class="row justify-content-center mt-3">
        <div class="col-md-6">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Rechercher..." id="search-bar">
            </div>
        </div>
    </div>

    <div class="row mt-4" id="article-container"></div>
</div>


	<script src="javascript/rechercherProduits.js"></script>
</body>
</html>