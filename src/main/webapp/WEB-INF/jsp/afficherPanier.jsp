<%@page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<link href="${pageContext.request.contextPath}/css/panier.css" rel="stylesheet" type="text/css" />
<title>Mon panier</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/navbar.jsp" />
	<div class="panier-container"></div>
	<script src="javascript/afficherPanier.js"></script>
</body>
</html>
