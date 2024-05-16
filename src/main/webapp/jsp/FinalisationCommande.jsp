<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sélection de magasin et de créneau</title>
</head>
<body>
    <h1>Sélection de magasin et de créneau</h1>
    <form action="FinaliserCommande" method="post">
        <label for="magasin">Sélectionnez un magasin :</label>
        <select name="magasin" id="magasin">
            <option value="1">SuperMarket Sud</option>
            <!-- Ajoutez d'autres options si nécessaire -->
        </select>
        <br><br>
        <label for="creneau">Sélectionnez un créneau :</label>
        <select name="creneau" id="creneau">
            <!-- Les créneaux peuvent être récupérés de la base de données via la servlet -->
        </select>
        <br><br>
        <input type="submit" value="Valider">
    </form>
</body>
</html>
