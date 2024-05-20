/**
 * JavaScript pour l'affichage dynamique des listes de courses dans la pop-up d'ajout d'un produit dans une liste
 */
function chargerListe(){
	// Objet XMLHttpRequest
	 var xhr = new XMLHttpRequest();
	 // Requête au serveur avec les paramètres éventuels.
   	xhr.open("GET", "/SupermarketG3/GenerationListeXml", true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            var xmlDoc = xhr.responseXML;
            if (xmlDoc === null) {
                console.error("xmlDoc is null. Check the response format.");
                return;
            }
            // Elément html que l'on va mettre à jour avec le XML reçu
            var shoppingLists = xmlDoc.getElementsByTagName("shoppingList");
            var optionsHTML = "<option>-- Choisir --</option>";
            for (var i = 0; i < shoppingLists.length; i++) {
                var id = shoppingLists[i].getElementsByTagName("id")[0].textContent;
                var name = shoppingLists[i].getElementsByTagName("name")[0].textContent;
                optionsHTML += "<option value='" + id + "'>" + name + "</option>";
            }
            //M-a-j de la liste de choix dans la modale d'ajout
            var selectList = document.getElementById("select_list");
            selectList.innerHTML = optionsHTML;
        } else {
            console.error("Failed to fetch data: " + xhr.status);
        }
    };
    // Envoie de la requete
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
        