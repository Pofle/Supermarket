<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
<title>Accueil</title>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<h1>Accueil</h1>
	<div class="search-bar">
		<input type="text" placeholder="Rechercher...">
		<button type="button">Rechercher</button>
	</div>
	
	<!-- ------------------------------------------------------------------------------------------------------ -->
	
	<div class="modal fade" id="exampleModalToggle" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
  		<div class="modal-dialog modal-dialog-centered">
    		<div class="modal-content">
      			<div class="modal-header">
        			<h1 class="modal-title fs-5" id="exampleModalToggleLabel">Modal 1</h1>
        			<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      			</div>
      			<div class="modal-body">
        			Show a second modal and hide this one with the button below.
      			</div>
      			<div class="modal-footer">
        			<button class="btn btn-primary" data-bs-target="#exampleModalToggle2" data-bs-toggle="modal">Open second modal</button>
      			</div>
    		</div>
  		</div>
	</div>
	<div class="modal fade" id="exampleModalToggle2" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalToggleLabel2">Modal 2</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        Hide this modal and show the first with the button below.
	      </div>
	      <div class="modal-footer">
	        <button class="btn btn-primary" data-bs-target="#exampleModalToggle" data-bs-toggle="modal">Back to first</button>
	      </div>
	    </div>
	  </div>
	</div>
	<button class="btn btn-primary" data-bs-target="#exampleModalToggle" data-bs-toggle="modal">Open first modal</button>
		
	<!-- ------------------------------------------------------------------------------------------------------ -->
	
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
			</div>
		</c:forEach>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
	</body>
</html>
