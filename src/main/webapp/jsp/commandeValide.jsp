<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmation de commande</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
            position: relative;
        }
        h1 {
            color: #4CAF50;
            text-align: center;
        }
        p {
            font-size: 1.1em;
            line-height: 1.6em;
            margin: 0 0 20px;
        }
        .recap {
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 8px;
            background-color: #fafafa;
        }
        .recap h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 1.5em;
            color: #333;
        }
        .recap ul {
            list-style: none;
            padding: 0;
        }
        .recap li {
            margin-bottom: 10px;
            font-size: 1.1em;
        }
        .recap li:before {
            content: '•';
            color: #4CAF50;
            font-weight: bold;
            display: inline-block;
            width: 1em;
            margin-left: -1em;
        }
        .home-button {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 20px;
            background-color: #4CAF50;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            position: absolute;
            bottom: 20px;
            right: 20px;
        }
        .home-button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Commande validée avec succès !!!</h1>
        <%
            LocalDate dateCommande = (LocalDate) request.getAttribute("dateCommande");
            LocalDate jourRetrait = (LocalDate) request.getAttribute("jourRetrait");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        %>
        <p>Merci pour votre commande du <%= dateCommande.format(formatter) %>. 
        Vous pourrez venir la récupérer à notre magasin "<%= request.getAttribute("nomMagasin") %>" à "<%= request.getAttribute("adresseMagasin") %>" 
        à "<%= request.getAttribute("horaireRetrait") %>" le "<%= jourRetrait.format(formatter) %>".</p>
        <div class="recap">
            <h2>Récapitulatif de votre Drive :</h2>
            <ul>
                <li>Date de commande : <%= dateCommande.format(formatter) %></li>
                <li>Magasin : <%= request.getAttribute("nomMagasin") %></li>
                <li>Adresse du magasin : <%= request.getAttribute("adresseMagasin") %></li>
                <li>Jour de retrait : <%= jourRetrait.format(formatter) %></li>
                <li>Horaire de retrait : <%= request.getAttribute("horaireRetrait") %></li>
            </ul>
        </div>
        <a href="central?type_action=accueil" class="home-button">Retour à l'accueil</a>
    </div>
</body>
</html>
