<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestionnaire - Gestion des produits</title>
</head>
<body>
	<c:if test="${requestScope.categorie == 'GESTIONNAIRE'}">
		<input type="file" id="fileInput" accept=".csv">
		<button id="uploadButton">Upload CSV</button>
		<p id="resultMessage" style="display: none;"></p>
		<script src="javascript/uploadCsv.js"></script>
	</c:if>
</body>
</html>