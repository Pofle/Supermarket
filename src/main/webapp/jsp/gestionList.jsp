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
<link href="css/accueil.css" rel="stylesheet" type="text/css" />
</head>

<title>Liste de courses</title>
<body>
<%@ include file="navbar.jsp"%>
    <h1>Mes listes de courses</h1>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>Hello world </p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
    
    <p>Les listes vous permettent de retrouver facilement les produits que vous souhaitez acheter. <br>
    Vous pouvez créer jusqu'à 10 listes différentes de 100 articles maximum.</p>
   
   <c:if test="${requestScope.categorie == 'UTILISATEUR'}">
    	<ul>
        	<c:forEach var="list" items="${shoppingLists}" varStatus="status" >
            	<li> ${status.index + 1} - ${list.name}</li>
        	</c:forEach>
    	</ul> 
    	<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">Ajouter liste</button>
   </c:if>  
    
    
    
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
   </body>
   
   
   </html>