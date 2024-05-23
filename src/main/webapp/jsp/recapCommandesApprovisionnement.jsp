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
                <th>Date d'arrivée</th>
                <th>Magasin</th>
                <th>Quantité commandée</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${approvisionnements}" var="approvisionnement">
                <tr>
                    <td>${approvisionnement[0]}</td>
                    <td>${approvisionnement[1]}</td>
                    <td>${approvisionnement[2]}</td>
                    <td>${approvisionnement[3]}</td>
                    <td>${approvisionnement[4]}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</body>
</html>
