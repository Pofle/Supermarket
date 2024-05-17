<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="/jsp/header.jsp" />
<title>Création de compte</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<br>
	
	<h2>Inscription</h2>

	<form method="post" action="/inscription">
		<label for="prenom">Prénom:</label>
		<input type="text" id="prenom" name="prenom" required><br> <br> 
		
		<label for="nom">Nom:</label>
		<input type="text" id="nom" name="nom" required><br> <br> 
		
		<label for="mail">Mail:</label> 
		<input type="text" id="mail" name="mail" required><br> <br> 
		
		<label for="password">Mot de passe:</label>
		<input type="password" id="password" name="password" required><br> <br> 
		
		<input type="submit" value="Se connecter">
	</form>
</body>
</html>