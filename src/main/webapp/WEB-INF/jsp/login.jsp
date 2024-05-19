<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authentification</title>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/navbar.jsp" />
	<br>

	<h2>Authentification</h2>

	<form method="post" action="/SupermarketG3/login">
		<label for="mail">Mail:</label>
		<input type="text" id="mail" name="mail" required><br> <br> 
		
		<label for="password">Mot de passe:</label> 
		<input type="password" id="password" name="password" required><br> <br> 
		
		<input type="submit" value="Se connecter">
	</form>
	<a href="jsp/inscription.jsp">Pas encore de compte ? Inscrivez vous</a>

	<%
	// Afficher les messages d'erreur ou de succès
	String message = (String) request.getAttribute("message");
	if (message != null) {
		out.println("<p>" + message + "</p>");
	}
	%>

</body>
</html>