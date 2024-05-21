/**
 * JavaScript pour l'affichage dynamique des listes de courses dans la pop-up d'ajout d'un produit dans une liste
 */
function chargerListe() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "generationListeXml", true);
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
        } else {
            console.error("Failed to fetch data: " + xhr.status);
        }
    };
    xhr.send();
}

/**
 * Fountion pour forcer la fermture de la modale
 */
function forcerFermetureModal() {
    $('#addProduitModal').on('hidden.bs.modal', function() {
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    });
}
/**
 * Appel de la fonction de fermeture
 */
$(document).ready(function() {
    forcerFermetureModal();
});


        