/**
 * Fonction pour générer les boutons "Memo" pour les listes de courses.
 */
function generateMemoButtons() {
    if (!memosXml) {
        console.error("XML_memos not found.");
        return;
    }
    // Parse le texte XML en document XML
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(memosXml, "application/xml");

    // Récupère tous les éléments <id_liste> dans le XML
    const idListElements = xmlDoc.getElementsByTagName("id_liste");
    const idListArray = Array.from(idListElements).map(el => el.textContent);

    // Pour chaque conteneur de bouton dans le DOM, ajoute le bouton approprié
    document.querySelectorAll('[id^=btn-Memo_container-]').forEach(container => {
        const listeId = container.id.split('-')[2]; // Extraire l'ID de liste depuis l'ID du conteneur
        if (idListArray.includes(listeId)) {
            // Génère le bouton "Memo" si l'ID_listeCourse est présent dans le XML
            const memoButton = document.createElement('a');
            memoButton.href = "";
            memoButton.setAttribute('data-bs-toggle', 'modal');
            memoButton.setAttribute('data-bs-target', '#modalMemo');
            memoButton.innerHTML = `<img src="recupererImage?cheminImage=icons/memo_icon2.png" class="btn-Memo" onclick="chargerMemo(${listeId})" title="Consulter son post-it" />`;
            container.appendChild(memoButton);
        } else {
            // Génère le bouton "Add Memo" si l'ID_ListeCourse n'est pas présent dans le XML
            const addMemoButton = document.createElement('a');
            addMemoButton.href = "";
            addMemoButton.setAttribute('data-bs-toggle', 'modal');
            addMemoButton.setAttribute('data-bs-target', '#modalMemo');
            addMemoButton.innerHTML = `<img src="recupererImage?cheminImage=icons/addMemo_icon.png" class="btn-addMemo" onclick="chargerMemo(${listeId})" title="Ajouter un post-it" />`;
            container.appendChild(addMemoButton);
        }
    });
}

/** Appel de la fonction de génération au chargement de la page */ 
window.onload = generateMemoButtons;

/**
 * Fonction pour charger le contenu de la modal memo
 */
function chargerMemo(listeId) {
    //log de controle
    console.log("ID reçu pour afficher la modale memo : " + listeId);

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "GenerationMemoXml?listeId=" + listeId);
    xhr.onload = function() {
        if (xhr.status === 200) {
            var xmlDoc = xhr.responseXML;
            if (xmlDoc === null) {
                console.error("xml_MemoContent is null. Check the response format.");
                return;
            }
            // Sélectionne le corps de la modale
            var modalBody = document.querySelector('#modalMemo .modal-body');
            modalBody.innerHTML = ''; // Vide le contenu actuel

            // Parcours les éléments XML pour créer les éléments HTML
            var memoNodes = xmlDoc.querySelectorAll('memo');
            memoNodes.forEach(function(memoNode) {
                var idMemo = memoNode.querySelector('id_memo').textContent;
                var libelle = memoNode.querySelector('libelle').textContent;
                var li = document.createElement('li');
                li.innerHTML = `
                	<input type="hidden" name="id_memo_${idMemo}" value="${idMemo}">
                    <input type="text" name="libelle_${idMemo}" value="${libelle}" required>
                    <a href="" onclick="supprimerMemo(${idMemo}, ${listeId})"><img src="recupererImage?cheminImage=icons/delete_icon.png" class="btn-DeleteProduit" title="Supprimer la ligne"></a>`;
                modalBody.appendChild(li);
            });
            // Ajout de la ligne pour ajouter un nouveau libellé
            var newForm = document.createElement('form');
            newForm.id = "addMemoForm";
            /*newForm.action="ServletGestionMemo"
            newForm.method = "post"*/
            newForm.innerHTML = `
            	<li>
                    <input type="hidden" name="id_memo" value="">
                    <input type="text" name="newLibelle" id="newLibelle" required>
                    <img src="recupererImage?cheminImage=icons/addLibelle_icon.png" data-liste-id="${listeId}" onclick="ajouterInputLibelle()" class="btn-AddInput" title="Ajouter une ligne">
                    <input type="hidden" name="listeId" id="listeIdInput" value="${listeId}">
                </li>
            `;
            modalBody.appendChild(newForm);
        } else {
            console.error("Failed to load memo content.");
        }
    };
    xhr.send();
}


function ajouterInputLibelle() {
	//event.preventDefault();
    console.log("Clic pour ajouter une entrée reçu");
   
    var form = document.getElementById('addMemoForm');
    form.action = 'ServletGestionMemo';
    form.method = 'post';
    form.submit();

   }
   
   function supprimerMemo(memoId, listeId) {
    console.log("Clic pour supprimer un mémo reçu : " + memoId);

    var xhr = new XMLHttpRequest();
    xhr.open("GET", `ServletGestionMemo?type_action=delete_memo&memoId=${memoId}&listeId=${listeId}`);
    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log("Mémo supprimé avec succès.");
            // Rechargez les mémos pour mettre à jour la liste
            chargerMemo(listeId);
        } else {
            console.error("Failed to delete memo.");
        }
    };
    xhr.send();
}
   
/**
 * Fonction pour afficher les produits d'une liste de course
 */
function chargerProduitsListe(idListe, nomListe) {
    // RETOUR CONSOLE pour test
    console.log("ID de la liste envoyé dans le js :" + idListe);
    console.log("Nom de la liste:" + nomListe);

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "generationListeProduitXml?id=" + idListe);
    xhr.onload = function() {
        if (xhr.status === 200) {
            var xmlDoc = xhr.responseXML;
            if (xmlDoc === null) {
                console.error("xmlDocListeProduits is null. Check the response format.");
                return;
            }
            // Generation du HTML selon le contenu du xml
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
                produitsHTML += "<a href='gestionProduitListe?type_action=delete_produit&produit_ean=" + ean + "&listeId=" + idListe + "'><img src='recupererImage?cheminImage=icons/delete_icon.png' class='btn-DeleteProduit' title='Supprimer le produit'/></a>";
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
 * Fonction convertir une liste de course en panier
 */
function convertirListeEnPanier() {
    var form = document.getElementById('formProduits');
    form.action = 'ServletConversionListe';
    form.method = 'post';
    form.submit();
}

/**
 * Fonction pour forcer la fermeture de la modale Bootstrap
 */
function forcerFermetureModal() {
    $('#modalProduits').on('hidden.bs.modal', function() {
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    });
}

/** Appel de la fonction de fermeture sur le document */
$(document).ready(function() {
    forcerFermetureModal();
});