<%@page import="java.util.*"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.text.DecimalFormat"%>
<%-- Définion d'un format pour les prix --%>
<%
request.setAttribute("decimalFormat", new DecimalFormat("#.00"));
%>
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

	<div class="search-bar">
		<input type="text" placeholder="Rechercher...">
		<button type="button">Rechercher</button>
	</div>


	<div class="article-container" id="article-container">
		<c:forEach var="produit" items="${produits}">
			<div class="article-card">
				<a href="/SupermarketG3/accueil?ean=${produit.getEan()}">

					<div class="product-info">
						<div class="product-image">
							<img src="${produit.getVignetteBase64()}" class="image" />
						</div>
						<div class="product-details">
							<p class="price">${decimalFormat.format(produit.getPrix())}€</p>
							<p class="libelle-marque">${produit.getLibelle()}-
								${produit.getMarque()}</p>
							<c:choose>
								<c:when test="${not empty produit.getConditionnement()}">
									<p class="additional-info">${produit.getConditionnement()}</p>
								</c:when>
								<c:otherwise>
									<c:set var="prixKilo"
										value="${produit.getPrix() * 1000 / produit.getPoids()}" />
									<p class="additional-info">${produit.getPoids()}g-
										${decimalFormat.format(prixKilo)}€/kg</p>
								</c:otherwise>
							</c:choose>
							<p class="nutriscore">Nutriscore: ${produit.getNutriscore()}</p>
						</div>
					</div>
				</a>
				<div>
					<input type="number" id="${produit.getEan()}" name="qte" min="0"
						value="0">
					<button onclick="ajouterProduit(${produit.getEan()})">Ajouter au panier</button>
				</div>
			</div>
		</c:forEach>
	</div>

	<script src="javascript/produits.js"></script>
</body>
</html>
