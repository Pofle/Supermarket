<%@page import="fr.miage.supermarket.models.Produit"%>
<%@page import="fr.miage.supermarket.models.Commande"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Préparation</title>
</head>
<body>
<h1>Préparation du panier</h1>
<h2>Panier n° <% request.getAttribute("Commande_enCours"); %></h2> 
<ul>
<% for (Produit p : (ArrayList<Produit>)request.getAttribute("Commande_enCours"))
	out.println("<li>" + p + "</li>");
%>
</ul>
</body>
</html>