/**
 * Script pour afficher les produits d'une liste de course
 */
function chargerProduitsListe(idListe) {
	 console.log("ID de la liste:" + idListe);
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "GestionProduitListe?id=" + idListe);

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function() {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200) {
            // Analyser la réponse XML
            var xmlDoc = xhr.responseXML;
            var produits = xmlDoc.getElementsByTagName("produit");
            var produitsHTML = "<ul>";
            for (var i = 0; i < produits.length; i++) {
                var libelle = produits[i].getElementsByTagName("libelle")[0].textContent;
                produitsHTML += "<li>" + libelle + "</li>";
            }
            produitsHTML += "</ul>";

            // Mettre à jour la modale avec les produits
            var modalBody = document.querySelector("#modalProduits .modal-body");
            modalBody.innerHTML = produitsHTML;

            // Afficher la modale
            var modal = new bootstrap.Modal(document.getElementById('modalProduits'));
            modal.show();
        }
    };

    // Envoie de la requête.
    xhr.send();
}