<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="fr.miage.supermarket.models.Magasin" %>
<%@ page import="fr.miage.supermarket.models.Jour" %>
<%@ page import="fr.miage.supermarket.models.Horaire" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%-- Définion d'un format pour les prix --%>
<%
    request.setAttribute("decimalFormat", new DecimalFormat("#.00"));
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp">
    <jsp:param name="title" value="Accueil" />
</jsp:include>
<link href="css/accueil.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
<title>Accueil</title>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    
    <!-- ------------------------------------------------------------------------------------------------------ -->
    
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
                <select name="magasin" id="magasin">
                    <option value="">Votre magasin</option>
                        <% for (Magasin magasin : (List<Magasin>)request.getAttribute("magasins")) { %>
                    <option value="<%= magasin.getId() %>"><%= magasin.getNom() %></option>
                        <% } %>
                </select>
                <br><br>
                <label for="date">Sélectionnez une date :</label>
                <input type="date" id="date" name="date" min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                <br><br>    
                <label for="horaire">Sélectionnez un horaire :</label>
                <select name="horaire" id="horaire">
                    <option value="">Heure de retrait</option>
                </select>
                <br><br>    
            </div>
          <div class="modal-footer">
            <button type="submit" value="Valider" class="btn btn-primary" data-dismiss="modal">Valider</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
          </div>
          </form>
        </div>
      </div>
    </div>
    <!-- ------------------------------------------------------------------------------------------------------ -->
    
    <div class="search-bar">
        <input type="text" placeholder="Rechercher...">
        <button type="button">Rechercher</button>
    </div>
    
    
    <div class="article-container" id="article-container">
        <c:forEach var="produit" items="${produits}">
            <div class="article-card">
                <a href="/SupermarketG3/accueil?ean=${produit.getEan()}">

                    <div class="product-info">
                        <div class="product-image">
                            <img src="${produit.getVignetteBase64()}" class="image" />
                        </div>
                        <div class="product-details">
                            <p class="price">${decimalFormat.format(produit.getPrix())}€</p>
                            <p class="libelle-marque">${produit.getLibelle()}-
                                ${produit.getMarque()}</p>
                            <c:choose>
                                <c:when test="${not empty produit.getConditionnement()}">
                                    <p class="additional-info">${produit.getConditionnement()}</p>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="prixKilo"
                                        value="${produit.getPrix() * 1000 / produit.getPoids()}" />
                                    <p class="additional-info">${produit.getPoids()}g-
                                        ${decimalFormat.format(prixKilo)}€/kg</p>
                                </c:otherwise>
                            </c:choose>
                            <p class="nutriscore">Nutriscore: ${produit.getNutriscore()}</p>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>

    <script>
    $(document).ready(function() {
        $('#date').change(function() {
            $('#horaire').empty();
            var selectedDate = new Date($(this).val());
            var today = new Date();
            today.setHours(0, 0, 0, 0);
            var currentHour = today.getHours();
            var debut = (selectedDate.getTime() === today.getTime()) ? currentHour + 1 : 9; // Heure de début
            var fin = 19; // Heure de fin

            // Ajout des horaires
            for (var i = debut; i <= fin; i++) {
                for (var j = 0; j < 60; j += 15) {
                    var heure = (i < 10) ? '0' + i : i;
                    var minute = (j === 0) ? '00' : j;
                    var horaire = heure + ':' + minute;
                    $('#horaire').append('<option value="' + horaire + '">' + horaire + '</option>');
                }
            }
        });
    });
</script>




    
</body>
</html>
