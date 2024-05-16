<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
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
		
	</body>
</html>