<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/header.jsp">
		<jsp:param name="title" value="Gestion du Stock" />
	</jsp:include>
    <title>Gestion des Stocks</title>
    <link href="css/stock-produit.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="navbar.jsp"%>
    <h1>Gestion des Stocks des 15 prochains jours</h1>
    <table border="1">
        <thead>
            <tr>
                <th>EAN</th>
                <th>Libelle</th>
                <th>Prix</th>
                <th>Date Stock</th>
                <th>Quantité</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${produitsStock}" var="produitStock">
                <c:choose>
                    <c:when test="${produitStock[4] == 0}">
                        <tr class="stock-rupture">
                    </c:when>
                    <c:when test="${produitStock[4] > 0 && produitStock[4] <= 5}">
                        <tr class="stock-faible">
                    </c:when>
                    <c:when test="${produitStock[4] > 5 && produitStock[4] <= 10}">
                        <tr class="stock-moyen">
                    </c:when>
                    <c:otherwise>
                        <tr>
                    </c:otherwise>
                </c:choose>
                    <td>${produitStock[0]}</td>
                    <td>${produitStock[1]}</td>
                    <td>${produitStock[2]}</td>
                    <td>${produitStock[3]}</td>
                    <td>${produitStock[4]}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>