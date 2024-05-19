/**
 * Script pour afficher les produits d'une liste de course
 */
function chargerProduitsListe(idListe, nomListe) {
    // RETOUR CONSOLE pour test
    console.log("ID requested:" + idListe);
      console.log("Nom de la liste:" + nomListe);
    // FIN RETOUR
    var xhr = new XMLHttpRequest();

    xhr.open("GET", "GestionProduitListe?id=" + idListe);
    xhr.onload = function() {
        if (xhr.status === 200) {
            var xmlDoc = xhr.responseXML;
            // Log de controle du chargement du xml
            if (xmlDoc === null) {
                console.error("xmlDoc is null. Check the response format.");
                return;
            }
            var produits = xmlDoc.getElementsByTagName("produit");
            var produitsHTML = "<ul>";
            for (var i = 0; i < produits.length; i++) {
                var libelle = produits[i].getElementsByTagName("libelle")[0].textContent;
                produitsHTML += "<li>" + libelle + "</li>";
            }
            produitsHTML += "<ul>";

            // Mise-a-jour et affichage de la modale
            var modalBody = document.querySelector("#modalProduits .modal-body");
            modalBody.innerHTML = produitsHTML;
            
            var modalTitle = document.querySelector("#ModalProduitsLabel");
            modalTitle.innerText = "La liste " + nomListe +" contient :"; 

            var modal = new bootstrap.Modal(document.getElementById('modalProduits'));
            modal.show();
        }
    };
    // Log de controle en cas d'échec de la requete
    xhr.onerror = function() {
        console.error("Request failed");
    };
    //Envoi de la requete
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