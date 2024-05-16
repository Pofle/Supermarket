<%@page import="fr.miage.supermarket.models.Commande"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<jsp:include page="/jsp/header.jsp">
		<jsp:param name="title" value="Gestion des produits" />
	</jsp:include>
	<title>Préparation</title>
</head>
	<body>
	<%@ include file="navbar.jsp"%>
	<br>
	<h1>Paniers à préparer</h1>
	<c:if test="${requestScope.categorie == 'PREPARATEUR'}">
		<ul>
			<% for (Commande c : (ArrayList<Commande>)request.getAttribute("ListeCommandes") )
				out.println("<li> ID : " + c.getIdCommande()+ " <br> Créneau : " + c.getCreneau() + "</li>");
			%>
		</ul>
	</c:if>
</body>
</html>