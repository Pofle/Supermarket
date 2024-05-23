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
    <link href="css/stock-produit.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <h1>Récapitulatif des commandes d'approvisionnement</h1>
    
    <table border="1" class="table-style">
        <thead>
            <tr>
                <th>EAN</th>
                <th>Produit</th>
                <th>Quantité Commandée</th>
                <th>Date Arrivée</th>
                <th>Magasin</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${approvisionnements}" var="approvisionnement">
                <tr>
                    <td>${approvisionnement.produit.ean}</td>
                    <td>${approvisionnement.produit.libelle}</td>
                    <td>${approvisionnement.quantiteCommandee}</td>
                    <td>${approvisionnement.dateArriveeStock}</td>
                    <td>${approvisionnement.magasin.nom}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</body>
</html>
