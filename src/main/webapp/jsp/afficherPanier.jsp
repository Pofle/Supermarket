<%@page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="fr.miage.supermarket.models.Magasin"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
request.setAttribute("decimalFormat", new DecimalFormat("#.00"));
%>


<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<title>Mon panier</title>
</head>
<body>
	<jsp:include page="/jsp/navbar.jsp" />
	<div class="container mt-4">
		<div class="row">
			<div class="col-lg-8 col-md-10 mx-auto">
				<c:choose>
					<c:when test="${!empty produits}">
						<h1 id="panier-title" class="mb-4">Votre panier</h1>
						<c:forEach var="produit" items="${produits.values()}">
							<div id="produit" class="card mb-3" data-ean="${produit.ean}">
								<div class="row no-gutters">
									<div class="col-md-4">
										<img src="recupererImage?cheminImage=${produit.image}"
											class="card-img" alt="${produit.libelle}">
									</div>
									<div class="col-md-8">
										<div class="card-body">
											<h5 class="card-title">${produit.libelle}</h5>
											<p class="card-text">
												<fmt:formatNumber value="${produit.prix}" type="currency"
													currencySymbol="€" minFractionDigits="2"
													maxFractionDigits="2" />
												${produit.conditionnement}
												<c:if test="${not empty produit.poids}">
                                                    - <fmt:formatNumber
														value="${produit.prix * 1000 / produit.poids}"
														type="currency" currencySymbol="€" minFractionDigits="2"
														maxFractionDigits="2" />/kg
                                                </c:if>
											</p>
											<c:if test="${not empty produit.tauxPromotion}">
												<div class="badge badge-success">${produit.tauxPromotion}%
													d'économies</div>
											</c:if>
											<div class="mt-3">
												<div class="btn-group" role="group">
													<button class="btn btn-outline-secondary btn-moins"
														data-ean="${produit.ean}">-</button>
													<input type="text"
														class="form-control text-center quantite-input"
														value="${produit.quantite}" disabled style="width: 50px;">
													<button class="btn btn-outline-secondary btn-plus"
														data-ean="${produit.ean}">+</button>
												</div>
											</div>
											<div class="mt-3">
												<c:if test="${not empty produit.tauxPromotion}">
													<p id="prixTotalProduit" class="text-muted">
														<s><fmt:formatNumber
																value="${produit.prix * produit.quantite}"
																type="currency" currencySymbol="€" minFractionDigits="2"
																maxFractionDigits="2" /></s>
													</p>
													<p id="prixTotalProduitPromo" class="font-weight-bold">
														<fmt:formatNumber
															value="${(produit.prix * produit.quantite) * (1 - produit.tauxPromotion / 100)}"
															type="currency" currencySymbol="€" minFractionDigits="2"
															maxFractionDigits="2" />
													</p>
												</c:if>
												<c:if test="${empty produit.tauxPromotion}">
													<p id="prixTotalProduit" class="font-weight-bold">
														<fmt:formatNumber
															value="${produit.prix * produit.quantite}"
															type="currency" currencySymbol="€" minFractionDigits="2"
															maxFractionDigits="2" />
													</p>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<h1>Votre panier est vide...</h1>
					</c:otherwise>
				</c:choose>
			</div>
			<c:if test="${!empty produits}">
				<div id="resume-container" class="col-lg-4 col-md-10 mx-auto mt-4">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Résumé</h5>
							<div class="d-flex justify-content-between">
								<p class="font-weight-bold">Total:</p>
								<p id="prixTotal" class="prixTotal font-weight-bold text-right">
									<fmt:formatNumber value="${totalPrix}" type="currency"
										currencySymbol="€" minFractionDigits="2" maxFractionDigits="2" />
								</p>
							</div>
							<c:if test="${not empty utilisateur}">
								<c:set var="maxReduction" value="${utilisateur.points / 10}" />
								<c:choose>
									<c:when test="${totalPrix >= maxReduction}">
										<c:set var="maxReduction" value="${maxReduction}" />
									</c:when>
									<c:otherwise>
										<c:set var="maxReduction" value="${totalPrix}" />
									</c:otherwise>
								</c:choose>
								<div class="form-group">
									<label for="pointsUtilises">Points de fidélité (${utilisateur.points}pts)</label> <input
										type="number" class="form-control" id="pointsUtilises"
										name="pointsUtilises" min="0" step="10"
										max="${maxReduction * 10}"
										onchange="adjustToNearestMultiple(this)">
								</div>
								<p id="reductionPoints" class="font-weight-bold"></p>
							</c:if>
							<button id="validerPanier" type="button"
								class="btn btn-primary btn-block" data-toggle="modal"
								data-target="#exampleModal">Valider le panier</button>
								<br>
							<button id="viderPanier" type="button" class="btn btn-danger btn-block">Vider le panier</button>
						</div>
					</div>
				</div>
			</c:if>

		</div>
	</div>

	<script src="javascript/afficherPanier.js"></script>

	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Planifiez votre
						retrait</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action="panier" method="post">
					<div class="modal-body">
						<label for="magasin">Sélectionnez un magasin :</label> <select
							name="magasin" id="magasin" required>
							<option value="">Votre magasin</option>
							<%
							for (Magasin magasin : (List<Magasin>) request.getAttribute("magasins")) {
							%>
							<option value="<%=magasin.getId()%>"><%=magasin.getNom()%></option>
							<%
							}
							%>
						</select> <br>
						<br><label for="date">Sélectionnez une date :</label> 
								<input type="date" id="date" name="date" min="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>" required> 
							<br>
							<br>
							<label for="horaire">Sélectionnez un horaire :</label> 
								<select name="horaire" id="horaire" required></select> 
							<br> 
							<br> 
						<input type="hidden" id="pointsUtilisesInput" name="pointsUtilises" value="0">
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">Valider</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Fermer</button>
					</div>
				</form>

			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>

	<script>
		$(document)
				.ready(
						function() {
							$('#date')
									.change(
											function() {
												// Vider les options existantes dans le select horaire
												$('#horaire').empty();

												// Récupérer la date sélectionnée
												var selectedDate = new Date($(
														this).val());
												selectedDate.setHours(0, 0, 0,
														0); // Réinitialiser l'heure de la date sélectionnée

												// Récupérer la date d'aujourd'hui
												var today = new Date();
												today.setHours(0, 0, 0, 0); // Réinitialiser l'heure de la date d'aujourd'hui

												// Récupérer l'heure actuelle
												var currentHour = new Date()
														.getHours();

												// Définir l'heure de début
												var debut;
												if (selectedDate.getTime() === today
														.getTime()) {
													debut = currentHour + 2;
												} else {
													debut = 9; // Heure de début par défaut si la date sélectionnée n'est pas aujourd'hui
												}

												var fin = 19; // Heure de fin

												// Ajouter les horaires au select
												for (var i = debut; i <= fin; i++) {
													for (var j = 0; j < 60; j += 15) {
														var heure = (i < 10) ? '0'
																+ i
																: i;
														var minute = (j === 0) ? '00'
																: j;
														var horaire = heure
																+ ':' + minute;
														$('#horaire')
																.append(
																		'<option value="' + horaire + '">'
																				+ horaire
																				+ '</option>');
													}
												}

												// Si aucune heure n'est ajoutée (le début est après la fin), ajouter un message
												if ($('#horaire').children().length === 0) {
													$('#horaire')
															.append(
																	'<option value="">Aucune heure disponible</option>');
												}
											});
						});
		document.getElementById("viderPanier").addEventListener("click", function() {
	        var xhr = new XMLHttpRequest();
	        xhr.open("GET", "panier?action=vider", true);
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
	                // Rafraîchir la page
	                window.location.reload();
	            }
	        };
	        xhr.send();
	    });
	</script>

</body>
</html>
