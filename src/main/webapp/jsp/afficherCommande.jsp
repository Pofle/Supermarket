<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="fr.miage.supermarket.models.Magasin"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
request.setAttribute("decimalFormat", new DecimalFormat("#.00"));
%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link href="css/commande.css" rel="stylesheet" type="text/css" />
<title>Commandes</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />

</body>
</html>