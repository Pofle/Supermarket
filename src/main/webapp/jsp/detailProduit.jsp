<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.DecimalFormat"%>
<%-- Définion d'un format pour les prix --%>
<%
request.setAttribute("decimalFormat", new DecimalFormat("#.00"));
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/header.jsp" />
	<link href="css/detail-produit.css" rel="stylesheet" type="text/css" />
	<title>Détail produit</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<div class="container">
		<div class="card product-details">
			<div class="product-image">
				<img src="${produit.getImageBase64()}" class="image" />
			</div>
			<div class="card-body product-info">
				<h1>${produit.getLibelle()}-${produit.getMarque()}</h1>
				<div class="description">
					<p>${produit.getDescription()}</p>
				</div>
				<p class="price">${decimalFormat.format(produit.getPrix())}€</p>
				<div class="additional-info">
					<p>
						<c:choose>
							<c:when test="${not empty produit.getConditionnement()}">
                            	${produit.getQuantiteConditionnement()} ${produit.getConditionnement()}
                        	</c:when>
							<c:otherwise>
								<c:set var="prixKilo"
										value="${produit.getPrix() * 1000 / produit.getPoids()}" />
	                            ${decimalFormat.format(produit.getPoids())}g - ${decimalFormat.format(prixKilo)}€/kg
	                        </c:otherwise>
						</c:choose>
					</p>
				</div>
				<p class="nutriscore">Nutriscore : ${produit.getNutriscore()}</p>
			</div>
		</div>
	</div>
	<c:if test="${not empty promotions}">
		<div class="promotion-section">

			<h2>Promotions en cours !</h2>
			<div class="promotion-table">
				<table>
					<thead>
						<tr>
							<th>Pourcentage</th>
							<th>Prend fin le</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="promotion" items="${promotions}">
							<tr>
								<td>${promotion.getPourcentage()}%</td>
								<td><fmt:formatDate value="${promotion.getDateFin()}"
										pattern="dd-MM-yyyy" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>
</body>
</html>
