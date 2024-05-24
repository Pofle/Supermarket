<%@page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link href="css/rayon.css" rel="stylesheet" type="text/css" />
<title>Nos rayons</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<div class="container mt-4">
		<h2>Nos rayons</h2>
		<div class="row">
			<div class="col-md-2">
				<ul class="list-group">
					<c:forEach var="rayon" items="${rayons}">
						<li class="list-group-item rayon-item" data-rayon-id="${rayon.id}">${rayon.libelle}</li>
					</c:forEach>
				</ul>
			</div>
			<div class="col-md-2" id="categories"></div>
			<div class="col-md-8" id="produits"></div>
		</div>
	</div>

	<script src="javascript/rayon.js"></script>
</body>
</html>