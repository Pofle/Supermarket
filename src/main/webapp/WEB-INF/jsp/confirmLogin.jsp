<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmation de Connexion</title>
    <jsp:include page="/WEB-INF/jsp/header.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
    <br>
    <h2>Confirmation de Connexion</h2>

    <%
        // Récupérer le message défini dans la servlet
        String message = (String) request.getAttribute("message");
        if (message != null) {
            out.println("<p>" + message + "</p>");
        } else {
            out.println("<p>Aucun message à afficher.</p>");
        }
    %>
</body>
</html>