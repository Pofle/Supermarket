<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Préparation</title>
</head>
<body>
<h1>Préparation du panier <% request.getAttribute("Commande_enCours").getCodeC() ; %></h1>
<ul>
<% for (Produit p : request.getAttribute("Commande_enCours").getPanier())
	out.println("<li>" + p.getCode()+ "</li>");
%>
</ul>
</body>
</html>