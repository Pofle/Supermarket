<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion du Stock</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>État du Stock pour les 15 Prochains Jours</h1>
    <table>
        <thead>
            <tr>
                <th>Produit</th>
                <c:forEach var="day" begin="0" end="14">
                    <th>Jour ${day + 1}</th> <!-- Jours indexés à partir de 1 -->
                </c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="produit" items="${produits}">
                <tr>
                    <td>${produit.libelle}</td>
                    <c:forEach var="day" begin="0" end="14">
                        <td>
                            <c:forEach var="stock" items="${produit.stocks}">
                                <c:if test="${stock.date eq (currentDate + day)}">
                                    ${stock.quantite}
                                </c:if>
                            </c:forEach>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>

