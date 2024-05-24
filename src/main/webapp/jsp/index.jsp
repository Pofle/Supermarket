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
                <select class="form-control" id="sort-options">
                    <option value="default">Trier par</option>
                    <option value="prixKilo-asc">Prix au kilo croissant</option>
                    <option value="prixKilo-desc">Prix au kilo décroissant</option>
                </select>
            </div>
        </div>
    </div>

    <div class="row mt-4" id="article-container"></div>
</div>

<div id="popup-data" data-show-popup="${showPopup}"></div>

<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Choisir le panier</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Voulez-vous garder votre panier précédent ou récupérer le panier associé à votre compte ?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="keepCart()">Garder le panier précédent</button>
                <button type="button" class="btn btn-secondary" onclick="retrieveCart()">Récupérer le panier associé au compte</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="javascript/rechercherProduits.js"></script>
<script src="javascript/popupPanier.js"></script>

</body>
</html>