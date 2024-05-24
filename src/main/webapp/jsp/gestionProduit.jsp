<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
		<jsp:include page="/jsp/header.jsp"/> 
	    <title>Gestion des produits</title>
	</head>
	<body>
		<jsp:include page="/jsp/navbar.jsp"/>
		<br>
		<h1>Gestion des produits</h1>
		<br>
		<c:if test="${requestScope.categorie == 'GESTIONNAIRE'}">
			<input type="file" id="fileInput" accept=".csv">
			<button id="uploadButton">Upload CSV</button>
			<p id="resultMessage" style="display: none;"></p>
			<script src="javascript/uploadCsv.js"></script>
		</c:if>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	</body>
</html>