/**
 * JavaScript pour l'affichage dynamique des listes de courses dans la pop-up d'ajout d'un produit dans une liste
 */

function chargerListe() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/SupermarketG3/GenerationListeXml", true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            var xmlDoc = xhr.responseXML;
            if (xmlDoc === null) {
                console.error("xmlDoc is null. Check the response format.");
                return;
            }
            var shoppingLists = xmlDoc.getElementsByTagName("shoppingList");
            var optionsHTML = "<option>-- Choisir --</option>";
            for (var i = 0; i < shoppingLists.length; i++) {
                var listeId = shoppingLists[i].getElementsByTagName("id")[0].textContent;
                var name = shoppingLists[i].getElementsByTagName("name")[0].textContent;
                optionsHTML += "<option value='" + listeId + "'>" + name + "</option>";
            }
            var selectList = document.getElementById("select_list");
            selectList.innerHTML = optionsHTML;
            
            /*// Initialise la modale
                    var modal = new bootstrap.Modal(document.getElementById('modalProduits'));
                    modal.show();
                    */
        } else {
            console.error("Failed to fetch data: " + xhr.status);
        }
    };
    xhr.send();
}

function forcerFermetureModal() {
    $('#addProduitModal').on('hidden.bs.modal', function() {
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    });
}

$(document).ready(function() {
    forcerFermetureModal();
});


        