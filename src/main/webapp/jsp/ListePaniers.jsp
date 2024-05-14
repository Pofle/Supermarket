<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Préparation</title>
</head>
<body>
<h1>Paniers à préparer</h1>
<ul>
<% for (Commande c : )
	out.println("<li>" + c.getCode()+ "</li>");
%>
</ul>
</body>
</html>