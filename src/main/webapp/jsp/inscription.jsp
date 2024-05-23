<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Création de compte</title>
<jsp:include page="/jsp/header.jsp" />
	<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">

</head>

<body>
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
	<jsp:include page="/jsp/navbar.jsp" />
	<br>

	<div class="container-sm">
	<h2>Inscription</h2>

	
	<form method="post" class="form-floating" action="/SupermarketG3/inscription"
		onsubmit="return validateForm()">
		<div class="mb-3">
			<label for="prenom" class="form-label">Prénom</label> 
			<input type="text" id="prenom" class="form-control" name="prenom" placeholder="Prénom" required>
		</div>
		<div class="mb-3">
			<label for="nom" class="form-label">Nom</label>
			<input type="text" id="nom" class="form-control" name="nom" placeholder="Nom" required>
		</div>

		<div class="mb-3">
			<label for="mail" class="form-label">Mail</label> 
			<input type="email" id="mail" class="form-control" name="mail" placeholder="Mail" required>
		</div>
		<div class="mb-3">
			<label for="password" class="form-label">Mot de passe</label> 
			<input type="password" id="password" class="form-control" name="password" placeholder="Mot de passe" required>
		</div>
		<div class="mb-3">
			<label for="confirmPassword" class="form-label">Confirmez le mot de passe</label>
			<input type="password" id="confirmPassword" class="form-control" name="confirmPassword" placeholder="Confirmation" required>
		</div>

		<div class="form-check mb-3">
			<input type="checkbox" id="personnalisation" class="form-check-input" value=""> 
			<label for="personnalisation" class="form-check-label">Acceptez-vous la personnalisation ?</label> 
		</div>

		<div class="mb-3">
			<button type="submit" class="btn btn-primary">S'inscrire</button>
		</div>
	</form>
	<%
	// Afficher les messages d'erreur ou de succès
	String message = (String) request.getAttribute("message");
	if (message != null) {
		out.println("<p>" + message + "</p>");
	}
	%>
	</div>
</body>
</html>