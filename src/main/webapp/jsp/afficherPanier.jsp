<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="fr.miage.supermarket.models.Magasin" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    request.setAttribute("decimalFormat", new DecimalFormat("#.00"));
%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
<link href="css/panier.css" rel="stylesheet" type="text/css" />
<title>Mon panier</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<div class="panier-container">
		<div class="produits-container"></div>
		<div class="resume-container" id="resume-container">
			<p id="prixTotal" class="prixTotal"></p>
			<button id="validerPanier" class="btn">Valider le panier</button>
		</div>
	</div>
	
	<script src="javascript/afficherPanier.js"></script>
	
	    
    <!-- Button trigger modal -->
    <div style="margin-left: 1cm;">
        <br/>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
          Drive 
        </button>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Planifiez votre retrait</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <form action="FinaliserCommande" method="post">
		    <div class="modal-body">
		        <label for="magasin">Sélectionnez un magasin :</label>
		        <select name="magasin" id="magasin" required>
		            <option value="">Votre magasin</option>
		            <% for (Magasin magasin : (List<Magasin>)request.getAttribute("magasins")) { %>
		                <option value="<%= magasin.getId() %>"><%= magasin.getNom() %></option>
		            <% } %>
		        </select>
		        <br><br>
		        <label for="date">Sélectionnez une date :</label>
		        <input type="date" id="date" name="date" min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" required>
		        <br><br>    
		        <label for="horaire">Sélectionnez un horaire :</label>
		        <select name="horaire" id="horaire" required>
		            <option value="">Heure de retrait</option>
		        </select>
		        <br><br>    
		    </div>
		    <div class="modal-footer">
		        <button type="submit" class="btn btn-primary">Valider</button>
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
		    </div>
		</form>

        </div>
      </div>
    </div>    
	
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>

    <script>
    $(document).ready(function() {
        $('#date').change(function() {
            // Vider les options existantes dans le select horaire
            $('#horaire').empty();
            
            // Récupérer la date sélectionnée
            var selectedDate = new Date($(this).val());
            selectedDate.setHours(0, 0, 0, 0); // Réinitialiser l'heure de la date sélectionnée
            
            // Récupérer la date d'aujourd'hui
            var today = new Date();
            today.setHours(0, 0, 0, 0); // Réinitialiser l'heure de la date d'aujourd'hui
            
            // Récupérer l'heure actuelle
            var currentHour = new Date().getHours();
            
            // Définir l'heure de début
            var debut;
            if (selectedDate.getTime() === today.getTime()) {
                debut = currentHour + 2;
            } else {
                debut = 9; // Heure de début par défaut si la date sélectionnée n'est pas aujourd'hui
            }
            
            var fin = 19; // Heure de fin

            // Ajouter les horaires au select
            for (var i = debut; i <= fin; i++) {
                for (var j = 0; j < 60; j += 15) {
                    var heure = (i < 10) ? '0' + i : i;
                    var minute = (j === 0) ? '00' : j;
                    var horaire = heure + ':' + minute;
                    $('#horaire').append('<option value="' + horaire + '">' + horaire + '</option>');
                }
            }

            // Si aucune heure n'est ajoutée (le début est après la fin), ajouter un message
            if ($('#horaire').children().length === 0) {
                $('#horaire').append('<option value="">Aucune heure disponible</option>');
            }
        });
    });
</script>
	
	
</body>
</html>
