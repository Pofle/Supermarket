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
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
	<%@ include file="navbar.jsp"%>
    <h1>Gestion des Stocks - Prochains 15 jours</h1>
    <table>
        <thead>
            <tr>
                <th>EAN</th>
                <th>Libellé</th>
                <th>Marque</th>
                <th>Quantité en stock</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="produit" items="${produits}">
                <tr>
                    <td>${produit.ean}</td>
                    <td>${produit.libelle}</td>
                    <td>${produit.marque}</td>
                    <td>
                        <c:forEach var="linkProduitStock" items="${produit.linkProduitStocks}">
                            <c:if test="${linkProduitStock.stock.date.time >= (currentTime.time - (currentTime.time % 86400000)) && linkProduitStock.stock.date.time <= (currentTime.time + 15*86400000)}">
                                ${linkProduitStock.qunatite} (au ${linkProduitStock.stock.date})
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>