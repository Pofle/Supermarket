<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/header.jsp">
		<jsp:param name="title" value="Gestion du Stock" />
	</jsp:include>
    <title>Récapitulatif des commandes d'approvisionnement</title>
    <link href="css/recap-approvisionnement.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <h1>Récapitulatif des commandes d'approvisionnement</h1>
    
    <table class="table-style">
        <thead>
            <tr>
                <th>EAN</th>
                <th>Produit</th>
                <th>Quantité Commandée</th>
                <th>Magasin</th>
                <th>Date Commande</th>
                <th>Date Arrivée</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="approvisionnement" items="${approvisionnements}">
                <tr>
                    <td>${approvisionnement.produit.ean}</td>
                    <td>${approvisionnement.produit.libelle}</td>
                    <td>${approvisionnement.quantite}</td>
                    <td>${approvisionnement.magasin.nom}</td>
                    <td>${approvisionnement.dateCommande}</td>
                    <td>${approvisionnement.dateArrivee}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</body>
</html>
