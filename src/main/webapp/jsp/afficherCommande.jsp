<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <div class="container">
        <h1>Liste des commandes</h1>
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>N° Commande</th>
                    <th>Magasin de retrait</th>
                    <th>Date de Retrait</th>
                    <th>Horaire de Retrait</th>
                    <th>Etat de la commande</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="commande" items="${commandes}">
                    <tr>
                        <td>${commande.id_commande}</td>
                        <td>${commande.magasin.nom}</td>
                        <td>${commande.dateRetrait}</td>
                        <td>${commande.horaireRetrait}</td>
                        <td>${commande.statut == nonValide ? 'Non validée' :
                              commande.statut == enCours ? 'En cours' :
                              commande.statut == pret ? 'Prête' :
                              commande.statut == termine ? 'Terminée' : 'Inconnu'}</td>
                        <td>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modifyModal"
                                    data-id="${commande.id_commande}"
                                    data-magasin="${commande.magasin.id}"
                                    data-date="${commande.dateRetrait}"
                                    data-horaire="${commande.horaireRetrait}">
                                Modifier
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="modifyModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modifyModalLabel">Modifier Commande</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="modifierCommande" method="post">
                    <input type="hidden" name="action" value="modifier">
                    <div class="modal-body">
                        <input type="hidden" id="idCommande" name="idCommande">
                        <div class="form-group">
                            <label for="magasin">Magasin de retrait</label>
                            <select class="form-control" id="magasin" name="magasinId" required>
                                <c:forEach var="magasin" items="${magasins}">
                                    <option value="${magasin.id}">${magasin.nom}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="dateRetrait">Modifiez la date :</label> 
							<input
							type="date" id="dateRetrait" name="dateRetrait"
							min="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"
							required> 
                        </div>
                        <div class="form-group">
                            <label for="horaireRetrait">Modifiez l'horaire :</label> 
							<select
							name="horaireRetrait" id="horaireRetrait" required>
							<option value="">Heure de retrait</option>
						</select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Sauvegarder</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js"
            integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js"
            integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
            crossorigin="anonymous"></script>

    <script>
        $('#modifyModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var id = button.data('id');
            var magasin = button.data('magasin');
            var dateRetrait = button.data('dateRetrait');
            var horaireRetrait = button.data('horaireRetrait');

            var modal = $(this);
            modal.find('#idCommande').val(id);
            modal.find('#magasin').val(magasin);
            modal.find('#dateRetrait').val(dateRetrait);
            modal.find('#horaireRetrait').val(horaireRetrait);
        });
    </script>
    
    <script>
		$(document)
				.ready(
						function() {
							$('#dateRetrait')
									.change(
											function() {
												// Vider les options existantes dans le select horaire
												$('#horaireRetrait').empty();

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
														$('#horaireRetrait')
																.append(
																		'<option value="' + horaire + '">'
																				+ horaire
																				+ '</option>');
													}
												}

												// Si aucune heure n'est ajoutée (le début est après la fin), ajouter un message
												if ($('#horaireRetrait').children().length === 0) {
													$('#horaireRetrait')
															.append(
																	'<option value="">Aucune heure disponible</option>');
												}
											});
						});
	</script>
    
</body>
</html>
