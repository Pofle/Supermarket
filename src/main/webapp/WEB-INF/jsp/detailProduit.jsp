<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.DecimalFormat"%>
<%-- Définion d'un format pour les prix --%>
<%request.setAttribute("decimalFormat", new DecimalFormat("#.00"));%>
<!DOCTYPE html>
<html>
<head>
<<<<<<< HEAD:src/main/webapp/WEB-INF/jsp/detailProduit.jsp
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<link href="${pageContext.request.contextPath}/css/detail-produit.css" rel="stylesheet" type="text/css" />
=======
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<jsp:include page="/jsp/header.jsp" />	
	<link href="css/detail-produit.css" rel="stylesheet" type="text/css" />
	<link href="css/navbar.css" rel="stylesheet" type="text/css" />
>>>>>>> developp:src/main/webapp/jsp/detailProduit.jsp
	<title>Détail produit</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/navbar.jsp" />
	<div class="container">
		<div class="card product-details">
			<div class="product-image">
				<img src="${produit.getImageBase64()}" class="image" />
			</div>
			<div class="card-body product-info">
				<h1>${produit.getLibelle()}-${produit.getMarque()}</h1>
				
				<!-- Bouton Ajouter produit à une liste -->
				<%-- <c:if test="${requestScope.categorie == 'UTILISATEUR'}"> --%>
					 <img onclick="chargerListe('${produit.getEan()}')" 
					 src="recupererImage?cheminImage=listIMG.png" class="img-AddIntoList" data-bs-toggle="modal" data-bs-target="#addProduitModal" />
				<%-- </c:if> --%>
				<!-- Fin du bouton	 -->	
				
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
	
	<!-- Affichage des promotions -->
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
	
	<!-- Modal d'ajout d'un produit dans une liste -->
  <div class="modal fade" id="addProduitModal" tabindex="-1" aria-labelledby="addProduitModalLabel" aria-hidden="true">
  	<div class="modal-dialog">
    	<div class="modal-content">
      	<div class="modal-header">
        	<h1 class="modal-title fs-5" id="addProduitModalLabel">Ajouter l'article dans une liste </h1>
        	<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      	</div>
      	
      	<form action="ServletAjoutProduitListe" method="post">
      		<div class="modal-body">
      			<label for="select-qte"> Choisir une quantité : </label>
        		<input type="number" id="input_qte" name="quantite" class="input-qte" required>                
        		<label for="select-liste"> Liste :</label>
        		<select name="select-liste" id="select_list">        		
        		</select>
        		<input type="hidden" name="type_action" value="add_produit">
        		<input type="hidden" name="produit_ean" value="${produit.getEan()}">	
      		</div>    		
      		<div class="modal-footer">
        		<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
        		<button type="submit" class="btn btn-primary">Enregistrer</button>       
      		</div>
      	</form>
      
    </div>
  </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="javascript/listeProduitScript.js"></script>  
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script> 
</body>
	
	
</html>
