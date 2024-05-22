<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authentification</title>
<jsp:include page="/jsp/header.jsp" />
    <link rel="stylesheet" 
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"  
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
    crossorigin="anonymous">
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	
	<br>

	<div class="container-sm">
	<h2>Authentification</h2>

	<form method="post" class="form-floating" action="/SupermarketG3/login">
		<div class="form-floating mb-3">
			<input type="email" class="form-control" id="mail" name="mail" placeholder="exemple@domaine.com" required>
			<label for="mail" class="form-label">Mail</label>
		</div>
		<div class="form-floating mb-3">
			<input type="password" class="form-control" id="password" name="password" placeholder="Mot de passe" required>
			<label for="password" class="form-label">Mot de passe</label>  
		</div>
		
		<div class="form-floating mb-3">
			<button type="submit" class="btn btn-primary">Se connecter</button>
		</div>
	</form>
	<a href="jsp/inscription.jsp" class="link-primary link-offset-3 link-underline-opacity-25 link-underline-opacity-100-hover">Pas encore de compte ? Inscrivez vous</a>

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