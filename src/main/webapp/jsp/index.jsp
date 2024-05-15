<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Accueil</title>
	
		<link href="css/accueil.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<h1>Accueil</h1>
		<ul>
			<li><a href="central?type_action=accueil">Accueil</a></li>
			<c:if test="${requestScope.categorie == 'GESTIONNAIRE'}">
				<li><a href="central?type_action=gestionProduit">Gérer les produits</a></li>
			</c:if>
		</ul>	    
	</body>
</html>
