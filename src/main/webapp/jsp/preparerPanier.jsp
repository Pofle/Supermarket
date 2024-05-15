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
<h2>Panier n°<% (Commande)request.getAttribute("Commande_enCours").getIdCommande(); %></h2> 
<ul>
<% for (Produit p : (ArrayList<Produit>)request.getAttribute("Commande_enCours").getPanier())
	out.println("<li>" + p.getEan()+ "</li>");
%>
</ul>
</body>
</html>