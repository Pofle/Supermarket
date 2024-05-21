<%@page import="fr.miage.supermarket.models.LinkCommandeProduit"%>
<%@page import="fr.miage.supermarket.models.Commande"%>
<%@page import="fr.miage.supermarket.models.Produit"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<link href="css/preparateur-liste.css" rel="stylesheet" type="text/css" />

<jsp:include page="/jsp/header.jsp">
	<jsp:param name="title" value="Accueil" />
</jsp:include>
<script>
document.addEventListener('DOMContentLoaded', function() {
	
	var commandCards = document.querySelectorAll('.command-card');
    var boutonPrepaTermine = document.querySelector('.button');

    function MajVisibilitePrepaBouton() {
        var selectedCheckboxes = document.querySelectorAll('.hidden-checkbox:checked');
        boutonPrepaTermine.style.display = selectedCheckboxes.length > 0 ? 'block' : 'none';
    }
    

    commandCards.forEach(function(card) {
        card.addEventListener('click', function() {
            // Toggle the 'selected' class
            card.classList.toggle('selected');
            
            // Find the checkbox inside the clicked card and toggle its checked state
            var hiddenCheckbox = card.querySelector('.hidden-checkbox');
            var selectedCards = document.querySelectorAll('.command-card.selected');
            if (selectedCards) {
                hiddenCheckbox.checked = !hiddenCheckbox.checked;
            }
            
            MajChronoPrepa();
            
            MajVisibilitePrepaBouton();
        });
    });
});

function MajChronoPrepa() {
    var selectedCards = document.querySelectorAll('.command-card.selected');
    var prepaChrono = document.getElementById('prepaChrono');

    if (selectedCards.length > 0) {
        prepaChrono.value = new Date().toISOString();
    }
}

function finChronoPrepa() {
    var finTempsPrepa = document.getElementById('finTempsPrepa');
    finTempsPrepa.value = new Date().toISOString();
}
MajVisibilitePrepaBouton();
</script>
<title>Préparation</title>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<% 	ArrayList<LinkCommandeProduit> linkAsso = (ArrayList<LinkCommandeProduit>) request.getAttribute("linkAsso"); %>
	<h1>Préparation du panier</h1>
	<h2>Panier n° <%= linkAsso.get(0).getCommande().getId_commande() %></h2> 
		<form action="preparerPanier" method="GET" onsubmit="finChronoPrepa()">
			<div class="command-container" id="command-container">
            	<% 
            		DecimalFormat decimalFormat = new DecimalFormat("#.##");
					for (LinkCommandeProduit ligne : linkAsso) {
						Produit produit = ligne.getProduit();
				%>
				<div class="command-card">
				 	<input type="checkbox" class="hidden-checkbox" name="produitValide" value="<%= ligne.getCommande().getId_commande() %>,<%= ligne.getProduit().getEan() %>">
					<div class="basket-info">
						<div class="basket-details">
							<p class="libelle-marque"><%= ligne.getQuantite()%> x <%= produit.getLibelle()%> -
								<%= produit.getMarque()%></p>
								<p class="price"><%= decimalFormat.format(produit.getPrix())%>€</p>
								<%
											String conditionnement = produit.getConditionnement();
											if (conditionnement != null && !conditionnement.isEmpty()) {
										%>
												<p class="additional-info"><%= conditionnement %></p>
										<%
											} else {
												double prixKilo = produit.getPrix() * 1000 / produit.getPoids();
										%>
												<p class="additional-info"><%= produit.getPoids() %>g - <%= decimalFormat.format(prixKilo) %>€/kg</p>
										<%
											}
										%>
							</div>
						</div>
					</div>
				<% } %>
			</div>
			<input type="hidden" id="prepaChrono" name="prepaChrono">
	        <input type="hidden" id="finTempsPrepa" name="finTempsPrepa">
	        <input type="submit" value="Préparation Terminée" class="button" style="display:none">
		</form>
</body>
</html>