<%@page import="fr.miage.supermarket.models.Commande"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Préparation</title>
</head>
<body>
	<h1>Paniers à préparer</h1>
	<ul>
		<% for (Commande c : (ArrayList<Commande>)request.getAttribute("ListeCommandes") )
			out.println("<li>" + c.getIdCommande()+ " " + c.getCreneau() + "</li>");
		%>
	</ul>
</body>
</html>