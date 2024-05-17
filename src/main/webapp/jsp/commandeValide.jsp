<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmation de commande</title>
    <!-- liens vers les fichiers CSS ou autres ressources -->
</head>
<body>
<%@ include file="navbar.jsp"%>
    <h1>Commande validée avec succès !!!</h1>
    <%
        LocalDate dateCommande = (LocalDate) request.getAttribute("dateCommande");
        LocalDate jourRetrait = (LocalDate) request.getAttribute("jourRetrait");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    %>
    <p>Merci pour votre commande du <%= dateCommande.format(formatter) %>. 
    Vous pourrez venir la récupérer à notre magasin "<%= request.getAttribute("nomMagasin") %>" à "<%= request.getAttribute("adresseMagasin") %>" 
    à "<%= request.getAttribute("horaireRetrait") %>" le "<%= jourRetrait.format(formatter) %>".</p>
    <h2>Récapitulatif de votre Drive :</h2>
    <ul>
        <li>Date de commande : <%= dateCommande.format(formatter) %></li>
        <li>Magasin : <%= request.getAttribute("nomMagasin") %></li>
        <li>Adresse du magasin : <%= request.getAttribute("adresseMagasin") %></li>
        <li>Jour de retrait : <%= jourRetrait.format(formatter) %></li>
        <li>Horaire de retrait : <%= request.getAttribute("horaireRetrait") %></li>
    </ul>
</body>
</html>
