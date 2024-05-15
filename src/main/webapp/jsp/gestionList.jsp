<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="fr.miage.supermarket.models.ShoppingList"%>
    <%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>Mes listes de courses</h1>
    
    <c:if test="${not empty msgError}">
        <p style="color:red;">${msgError}</p>
    </c:if>
    
    <p>Les listes vous permettent de retrouver facilement les produits que vous souhaitez acheter.
    Vous pouvez créer jusqu'à 10 listes différentes de 100 articles maximum.</p>
    

        <%for(ShoppingList sl : (List<ShoppingList>)request.getAttribute("shoppingLists")) {%>
    <ul>
    	<li> <%=sl.getName() %> </li>
    </ul>
    <%} %>
    
    
    
</body>
</html>