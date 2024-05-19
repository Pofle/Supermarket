/**
 * Script pour afficher les produits d'une liste de course
 */
function chargerProduitsListe(idListe, nomListe) {
    // RETOUR CONSOLE pour test
    console.log("ID de la liste envoyé dans le js :" + idListe);
    console.log("Nom de la liste:" + nomListe);
    // FIN RETOUR
    
   var xhr = new XMLHttpRequest();
            xhr.open("GET", "GestionProduitListe?id=" + idListe);
            xhr.onload = function() {
                if (xhr.status === 200) {
                    var xmlDoc = xhr.responseXML;
                    if (xmlDoc === null) {
                        console.error("xmlDoc is null. Check the response format.");
                        return;
                    }
                    // Generation du HTML
                    var produits = xmlDoc.getElementsByTagName("produit");
                    var produitsHTML = "<ul>";
                    for (var i = 0; i < produits.length; i++) {
                        var libelle = produits[i].getElementsByTagName("libelle")[0].textContent;
                        var marque = produits[i].getElementsByTagName("marque")[0].textContent;
                        var quantite = produits[i].getElementsByTagName("quantite")[0].textContent;
                        var ean = produits[i].getElementsByTagName("ean")[0].textContent;
                        produitsHTML += "<li>";
                        produitsHTML += "<input type='number' min='0' step='1' class='input_quantite' name='" + ean + "' value='" + quantite + "'>";
                        produitsHTML += "<p>" + libelle + " - " + marque + "</p>";
                        produitsHTML += "</li>";
                    }
                    produitsHTML += "</ul>";

					// Maj de la modale
                    var modalBody = document.querySelector("#modalProduits .modal-body");
                    modalBody.innerHTML = produitsHTML;
                    // Maj du titre de la modale
                    var modalTitle = document.querySelector("#ModalProduitsLabel");
                    modalTitle.innerText = "La liste " + nomListe + " contient :";
                    
         			// Récupère l'id de la liste
                    document.getElementById("listeId").value = idListe;
                    
                    // Initialise la modale
                    var modal = new bootstrap.Modal(document.getElementById('modalProduits'));
                    modal.show();
                }
            };
            xhr.send();
        }

/**
 * Fonction pour forcer la fermeture de la modale boostrap
 */
function forcerFermetureModal() {
    $('#modalProduits').on('hidden.bs.modal', function() {
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    });
}
$(document).ready(function() {
    forcerFermetureModal();
});