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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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
						<div class="product-head">
							<p class="price">${decimalFormat.format(produit.getPrix())}€</p>							
							<c:if test="${requestScope.categorie == 'UTILISATEUR'}">
							<a href="central?type_action=addToList" class="btn-Add-IntoList" data-bs-toggle="modal" data-bs-target="#exampleModal"  Title="Ajouter à une liste"> 
								<img src="recupererImage?cheminImage=listIMG.png" class="img-AddIntoList" />
							</a>
							</c:if>
							
						</div>
						<div class="product-details">
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
	
	<!-- Modal des listes de courses -->
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  	<div class="modal-dialog">
    	<div class="modal-content">
      	<div class="modal-header">
        	<h1 class="modal-title fs-5" id="exampleModalLabel">Ajouter l'article dans une liste </h1>
        	<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      	</div>
      	<form action="" method="">
      		<div class="modal-body">
      			<label for="select-qte"> Choisir une quantité : </label>
        		<input type="number" id="input_qte" class="input-qte" required>                
        		<label for="select-liste"> Liste :</label>
        		<select name="select-liste" id="select_list">
        		<option value=""> -- Choisir -- </option>
        		
        		<!-- ICI INTEGRATION DU XML DES LISTE DE COURSES ICI <--------> 
        		<c:forEach var="shoppingList" items="${shoppingLists}">
                        <option value="${shoppingList.id}"> ${shoppingList.name}</option>
                    </c:forEach>  
                </select>    		
      		</div>
      		
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
        <button type="submit" class="btn btn-primary">Enregistrer</button>       
      </div>
      </form>
    </div>
  </div>
</div>
			
		
	<script src="javascript/script.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
   	    
	<head>
		<jsp:include page="/jsp/header.jsp" />
		<link href="css/accueil.css" rel="stylesheet" type="text/css" />
		<title>Accueil</title>
	</head>
	<body>
		<jsp:include page="/jsp/navbar.jsp" />
		<div class="search-bar" >
			<input type="text" placeholder="Rechercher..." id="search-bar">
		</div>
		<div class="article-container" id="article-container">
		</div>
		<script src="javascript/rechercherProduits.js"></script>
	</body>
</html>
