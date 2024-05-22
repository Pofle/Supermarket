<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="/jsp/header.jsp" />
<title>Création de compte</title>
</head>
<style>
/* Container to position the tooltip relative to the input */
.tooltip-container {
	position: relative;
	display: inline-block;
}

/* Tooltip text */
.tooltip-text {
	visibility: hidden;
	width: 140px;
	background-color: white;
	color: black;
	text-align: center;
	border-style: solid;
	border-radius: 6px;
	border-color: black;
	border-width: 2px;
	padding: 5px;
	position: absolute;
	z-index: 1;
	bottom: 125%; /* Position the tooltip above the input */
	left: 50%;
	margin-left: -70px; /* Center the tooltip */
	opacity: 0;
/* 	transition: opacity 0.5s; */
	transition-duration: 0.2s;
}

/* Tooltip arrow */
.tooltip-text::after {
	content: "";
	position: absolute;
	top: 100%; /* At the bottom of the tooltip */
	left: 50%;
	margin-left: -5px;
	border-width: 5px;
	border-style: solid;
	border-color: black transparent transparent transparent;
}

/* Show the tooltip text when hovering over the input */
.tooltip-input:hover+.tooltip-text {
	visibility: visible;
	opacity: 1;
}
</style>
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
	<form method="post" action="/SupermarketG3/inscription"
		onsubmit="return validateForm()">
		<label for="prenom">Prénom:</label> <input type="text" id="prenom"
			name="prenom" required><br> <br> <label for="nom">Nom:</label>
		<input type="text" id="nom" name="nom" required><br> <br>

		<label for="mail">Mail:</label> <input type="text" id="mail"
			name="mail" required><br> <br> <label
			for="password">Mot de passe:</label> <input type="password"
			id="password" name="password" required><br> <br> <label
			for="confirmPassword">Confirmez le mot de passe:</label> <input
			type="password" id="confirmPassword" name="confirmPassword" required><br>
		<br>

		<div class="tooltip-container">
			<label for="personnalisation">Acceptez-vous la
				personnalisation ?</label> <input type="checkbox" id="personnalisation"
				class="tooltip-input"> <span class="tooltip-text">La
				personnalisation permet de vous proposer des produits de
				remplacement qui conviennent à vos habitudes en cas de rupture de
				stock</span>
		</div><br> <br>

		<input type="submit" value="S'inscrire">
	</form>
	<%
	// Afficher les messages d'erreur ou de succès
	String message = (String) request.getAttribute("message");
	if (message != null) {
		out.println("<p>" + message + "</p>");
	}
	%>
</body>
</html>