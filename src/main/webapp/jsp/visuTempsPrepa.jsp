<%@page import="fr.miage.supermarket.models.Commande"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<link href="css/preparateur-liste.css" rel="stylesheet" type="text/css" />
		<jsp:include page="/jsp/header.jsp">
			<jsp:param name="title" value="Temps de préparation" />
		</jsp:include>
		<title>Temps de préparation</title>
	</head>
	<body>
		<%@ include file="navbar.jsp"%>
		<br>
		<h1>Temps de préparation moyens des paniers</h1>
		<c:if test="${requestScope.categorie == 'GESTIONNAIRE'}">
			<p> Temps moyen de préparation = ${ moyenne } secondes </p>
		</c:if>
	</body>
</html>