<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<link href="css/panier.css" rel="stylesheet" type="text/css" />
<title>Mon panier</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<div class="panier-container">
		<div class="produits-container"></div>
		<div class="resume-container" id="resume-container">
			<p id="prixTotal" class="prixTotal"></p>
			<button id="validerPanier" class="btn">Valider le panier</button>
		</div>
	</div>
	
	<script src="javascript/afficherPanier.js"></script>
</body>
</html>
