<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@page import="fr.miage.supermarket.models.ShoppingList"%>
    <%@page import="java.util.List"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<jsp:include page="/jsp/header.jsp">
	<jsp:param name="title" value="Accueil" />
</jsp:include>
<title>Liste de courses</title>
<link href="css/accueil.css" rel="stylesheet" type="text/css" />
<link href="css/listeCourse.css" rel="stylesheet" type="text/css" />

</head>

<body>
<%@ include file="navbar.jsp"%>
    <h1>Mes listes de courses</h1>
    
    <p>Les listes vous permettent de retrouver facilement les produits que vous souhaitez acheter. <br>
    Vous pouvez créer jusqu'à 10 listes différentes de 100 articles maximum.</p>
    
   <div class="listes">
   	<c:if test="${requestScope.categorie == 'UTILISATEUR'}">
    	<ul>
        	<c:forEach var="list" items="${shoppingLists}" varStatus="status" >
            	<li> 
            		<p class="btn" data-bs-toggle="modal" data-bs-target="#modalProduits">${status.index + 1} - ${list.name}  </p>
            		<a href="ServletListeCourse?type_action=delete_list&list_id=${list.id}">
            			<img src="recupererImage?cheminImage=delete_icon.png" class="btn-Delete" Title="Supprimer la liste de course" />
            		</a>
            	</li> 
        	</c:forEach>
    	</ul>  
    		<button type="button" class="btn-Add" data-bs-toggle="modal" data-bs-target="#exampleModal">Ajouter liste</button> 	
    		
   	</c:if>    
   </div>
      
    <!-- Modal des listes de courses -->
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  	<div class="modal-dialog">
    	<div class="modal-content">
      	<div class="modal-header">
        	<h1 class="modal-title fs-5" id="exampleModalLabel">Ajouter une liste</h1>
        	<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      	</div>
      	<form action="ServletListeCourse" method="post">
      		<div class="modal-body">                
        		<label for="name">Nom :</label>
        		<input type="text" id="name" name="inputName" required>  	
      		</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
        <button type="submit" class="btn btn-primary">Enregistrer</button>       
      </div>
      </form>
    </div>
  </div>
</div>
  
  <!-- Modal produits dans la liste de course -->
<div class="modal fade" id="modalProduits" tabindex="-1" aria-labelledby="ModalProduitsLabel" aria-hidden="true">
  	<div class="modal-dialog">
    	<div class="modal-content">
      	<div class="modal-header">
        	<h1 class="modal-title fs-5" id="ModalProduitsLabel">Cette liste contient </h1>
        	<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      	</div>
      	<form action="" method="">
      		<div class="modal-body">                
        		  	
      		</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
        <button type="submit" class="btn btn-primary">Enregistrer</button>       
      </div>
      </form>
    </div>
  </div>
</div>
       
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
   </body>
     
</html>