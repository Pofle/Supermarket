<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@page import="fr.miage.supermarket.models.ShoppingList"%>
    <%@page import="java.util.List"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>Mes listes de courses</h1>
   
    
    <p>Les listes vous permettent de retrouver facilement les produits que vous souhaitez acheter. <br>
    Vous pouvez créer jusqu'à 10 listes différentes de 100 articles maximum.</p>
   
   <c:if test="${requestScope.categorie == 'UTILISATEUR'}">
    	<ul>
        	<c:forEach var="list" items="${shoppingLists}" varStatus="status" >
            	<li> ${status.index + 1} - ${list.name}</li>
        	</c:forEach>
    	</ul> 
    </c:if>  
    
    <c:if test="${requestScope.categorie == 'VISITEUR'}">
    	<p> <a href="">Connectez-vous</a> pour consulter vos listes, les modifier ou les ajouter à votre panier.</p>
    </c:if>
    
    <p> <a href="central?type_action==BackToHome">Retour à la page d'accueil</a></p>

    
    <c:if test="${not empty msgError}">
        <p style="color:red;">${msgError}</p>
    </c:if>
</body>
</html>