<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/header.jsp">
		<jsp:param name="title" value="Gestion du Stock" />
	</jsp:include>
    <title>Gestion des Stocks</title>
    <link href="css/stock-produit.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<c:set var="todayDate" value="${todayDate}" />
    <h1>Stocks des produits</h1>
    
    <select id="magasin" name="magasin">
        <option value="">Sélectionnez un magasin</option>
        <c:forEach var="magasin" items="${magasins}">
            <option value="${magasin.nom}">${magasin.nom}</option>
        </c:forEach>
    </select>
    
   <select id="dateStock" name="dateStock">
        <option value="">Sélectionnez une date</option>
        <c:forEach var="date" items="${datesStock}">
            <option value="${date}">${date}</option>
        </c:forEach>
    </select>
    
    <form id="approvisionnementForm" method="post" action="approvisionnement">
        <button type="submit" id="validerCommande">Valider la commande d'approvisionnement</button>
    
    <table class="table-style">
        <thead>
            <tr>
                <th>EAN</th>
                <th>Produit</th>
                <th>Marque</th>
                <th>Label</th>
                <th>Prix</th>
                <th>Poids</th>
                <th>Conditionnement</th>
                <th>Quantité</th>
                <th>Magasin</th>
                <th>Date du stock</th>
                <th>Quantité à réapprovisionner</th>
            </tr>
        </thead>
        <tbody id="tableBody">
            <c:forEach items="${produitsStock}" var="produitStock">
            	<%-- Niveaux d'alertes pour les quantités de stock faibles --%>
                <c:choose>
                    <c:when test="${produitStock[1] == 0}">
                        <tr class="stock-rupture">
                    </c:when>
                    <c:when test="${produitStock[1] > 0 && produitStock[1] <= 5}">
                        <tr class="stock-faible">
                    </c:when>
                    <c:when test="${produitStock[1] > 5 && produitStock[1] <= 10}">
                        <tr class="stock-moyen">
                    </c:when>
                    <c:otherwise>
                        <tr>
                    </c:otherwise>
                </c:choose>
                    <td>${produitStock[0].ean}</td>
                    <td>${produitStock[0].libelle}</td>
                    <td>${produitStock[0].marque}</td>
                    <td>${produitStock[0].label}</td>
                    <td>${produitStock[0].prix}</td>
                    <td>${produitStock[0].poids}</td>
                    <td>${produitStock[0].conditionnement}</td>
                    <td>${produitStock[1]}</td>
                    <td>${produitStock[2]}</td>
                    <td>${produitStock[3]}</td>
                    <td>
                        <%-- Vérification de la quantité en stock --%>
                        <c:if test="${produitStock[1] <= 10 && produitStock[3] == todayDate}">
                            <%-- Bouton + pour augmenter la quantité --%>
                            <button type="button" onclick="updateQuantity('${produitStock[0].ean}', '${produitStock[2]}', '${produitStock[3]}', 1)">+</button>
                            <%-- Input pour afficher la quantité à commander --%>
                            <input type="text" id="quantity_${produitStock[0].ean}_${produitStock[2]}" name="quantiteCommandee_${produitStock[0].ean}_${produitStock[2]}" value="0" />
                            <%-- Bouton - pour diminuer la quantité --%>
                            <button type="button" onclick="updateQuantity('${produitStock[0].ean}', '${produitStock[2]}', '${produitStock[3]}', -1)">-</button>
                            <%-- Champs cachés pour le formulaire --%>
                            <input type="hidden" name="ean" value="${produitStock[0].ean}" />
                            <input type="hidden" name="magasin" value="${produitStock[2]}" />
                            <input type="hidden" name="dateStock" value="${produitStock[3]}" />
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </form>
    
    <script>
        document.getElementById("dateStock").addEventListener("change", updateResults);
        document.getElementById("magasin").addEventListener("change", updateResults);

        function updateResults() {
        	var selectedDate = document.getElementById("dateStock").value;
            var selectedMagasin = document.getElementById("magasin").value;
            var rows = document.querySelectorAll("#tableBody tr");

            rows.forEach(function(row) {
                var rowDate = row.querySelector("td:nth-child(10)").textContent;
                var rowMagasin = row.querySelector("td:nth-child(9)").textContent;
                var dateMatch = selectedDate === "" || selectedDate === rowDate;
                var magasinMatch = selectedMagasin === "" || selectedMagasin === rowMagasin;

                if (dateMatch && magasinMatch) {
                    row.style.display = "table-row";
                } else {
                    row.style.display = "none";
                }
            });
        }
        
        function updateQuantity(ean, magasin, dateStock, increment) {
            var inputQuantity = document.getElementById("quantity_" + ean + "_" + magasin);
            var currentQuantity = parseInt(inputQuantity.value);
            var updatedQuantity = currentQuantity + increment;
            if (updatedQuantity < 0) {
                updatedQuantity = 0;
            }
            inputQuantity.value = updatedQuantity;
        }
        
    </script>
    
</body>
</html>