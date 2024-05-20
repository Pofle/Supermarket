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
		<jsp:param name="title" value="Préparer les commandes" />
	</jsp:include>
	<title>Préparation</title>
</head>
	<body>
	<%@ include file="navbar.jsp"%>
	<br>
	<h1>Paniers à préparer</h1>
	<c:if test="${requestScope.categorie == 'PREPARATEUR'}">
		
		<div class="command-container" id="command-container">
		 <%
                ArrayList<Commande> commandes = (ArrayList<Commande>) request.getAttribute("commandes");
                for (int i = 0; i < commandes.size(); i++) {
                    Commande commande = commandes.get(i);
            %>
                <div class="command-card">
                    <a href="/SupermarketG3/listePaniers?id_commande=<%= commande.getId_commande() %>">
                        <div class="basket-info">
                            <div class="basket-details">
                                <p class="libelle-marque">ID : <%= commande.getId_commande() %></p>
                                <p class="price">Créneau : <%= commande.getCreneau() %></p>
                            </div>
                        </div>
                    </a>
                </div>
            <%
                }
            %>
	</div>
	</c:if>
</body>
</html>