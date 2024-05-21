<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/jsp/header.jsp" />
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
          crossorigin="anonymous">
    <link href="css/commande.css" rel="stylesheet" type="text/css" />
    <title>Commandes</title>
</head>
<body>
    <jsp:include page="/jsp/navbar.jsp" />

    <div class="container">
        <h1>Liste des commandes</h1>
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>N° Commande</th>
                    <th>Magasin de retrait</th>
                    <th>Date de Retrait</th>
                    <th>Horaire de Retrait</th>
                    <th>Etat de la commande</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="commande" items="${commandes}">
                    <tr>
                        <td>${commande.id_commande}</td>
                        <td>${commande.magasin.nom}</td>
                        <td>${commande.dateRetrait}</td>
                        <td>${commande.horaireRetrait}</td>
                        <td>${commande.statut ? 'Completed' : 'Pending'}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
