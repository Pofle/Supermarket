<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="/jsp/header.jsp" />
<title>Cr�ation de compte</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<br>
	
	<h2>Inscription</h2>

	<script>
	function validateForm() {
	    var password = document.getElementById("password").value;
	    var confirmPassword = document.getElementById("confirmPassword").value;
	    if (password !== confirmPassword) {
	        alert("Les mots de passe ne correspondent pas.");
	        return false;
	    }
	    return true;
	}
	</script>
	<form method="post" action="/SupermarketG3/inscription" onsubmit="return validateForm()">
		<label for="prenom">Pr�nom:</label>
		<input type="text" id="prenom" name="prenom" required><br> <br> 
		
		<label for="nom">Nom:</label>
		<input type="text" id="nom" name="nom" required><br> <br> 
		
		<label for="mail">Mail:</label> 
		<input type="text" id="mail" name="mail" required><br> <br> 
		
		<label for="password">Mot de passe:</label>
		<input type="password" id="password" name="password" required><br> <br> 
		
		<label for="confirmPassword">Confirmez le mot de passe:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>
        
		<input type="submit" value="Se connecter">
	</form>
</body>
</html>