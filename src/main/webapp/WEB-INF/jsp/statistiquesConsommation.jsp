<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/jsp/header.jsp" />
<title>Statistiques de consommation</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<div class="container">
		<h1>Statistiques de consommation</h1>

		<h2>Répartition par catégorie</h2>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Catégorie</th>
					<th>Quantité</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="stat" items="${statistiques.categories}">
					<tr>
						<td>${stat[0]}</td>
						<td>${stat[1]}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<h2>Répartition par nutriscore</h2>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Nutriscore</th>
					<th>Quantité</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="stat" items="${statistiques.nutriscores}">
					<tr>
						<td>${stat[0]}</td>
						<td>${stat[1]}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<h2>Produits Bio</h2>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Label</th>
					<th>Quantité</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="stat" items="${statistiques.bio}">
					<tr>
						<td>${stat[0]}</td>
						<td>${stat[1]}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<h2>Répartition par marque</h2>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Marque</th>
					<th>Quantité</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="stat" items="${statistiques.marques}">
					<tr>
						<td>${stat[0]}</td>
						<td>${stat[1]}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
