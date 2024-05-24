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

var id = null

/**
 * Fonction pour charger le contenu de la modal memo
 */
function chargerMemo(listeId) {
	console.log("ID reçu pour afficher la modale memo : " + listeId);
	id = listeId;
	// Mettre à jour l'input hidden avec l'ID de la liste de courses
	document.getElementById('listeIdInput').value = listeId;

	var xhr = new XMLHttpRequest();
	xhr.open("GET", "GenerationMemoXml?listeId=" + listeId);
	xhr.onload = function() {
		if (xhr.status === 200) {
			var xmlDoc;
			try {
				xmlDoc = xhr.responseXML;
				if (!xmlDoc || !xmlDoc.documentElement) {
					throw new Error("Invalid XML format");
				}
			} catch (e) {
				console.error("Failed to parse XML:", e);
				return;
			}

			var modalBody = document.querySelector('#modalMemo .modal-body');
			modalBody.innerHTML = '';

			var memoNodes = xmlDoc.querySelectorAll('memo');
			memoNodes.forEach(function(memoNode) {
				var idMemo = memoNode.querySelector('id_memo').textContent;
				var libelle = memoNode.querySelector('libelle').textContent;
				var li = document.createElement('li');
				li.innerHTML = `
                        <p>${libelle}</p>
                        <a href="#" onclick="supprimerMemo(${idMemo}, ${listeId}); return false;">
                            <img src="recupererImage?cheminImage=icons/delete_icon.png" class="btn-DeleteProduit" title="Supprimer la ligne">
                        </a>`;
				modalBody.appendChild(li);
			});
			var newForm = document.createElement('form');
			newForm.id = "addMemoForm";
			newForm.innerHTML = `
                    <li>
                        <input type="hidden" name="id_memo" value="">
                        <input type="text" name="newLibelle" id="newLibelle" required>
                        <img src="recupererImage?cheminImage=icons/addLibelle_icon.png" data-liste-id="${listeId}" onclick="ajouterInputLibelle()" class="btn-AddInput" title="Ajouter une ligne">
                        <input type="hidden" name="listeId" id="listeIdInput" value="${listeId}">
                    </li>
                `;
			modalBody.appendChild(newForm);
			
			toggleConvertButton(memoNodes.length > 0);
		} else {
			console.error("Failed to load memo content.");
		}
	};
	xhr.send();
}

function supprimerMemo(memoId, listeId) {
	console.log("Clic pour supprimer un mémo reçu : " + memoId);

	var xhr = new XMLHttpRequest();
	xhr.open("GET", `ServletGestionMemo?type_action=delete_memo&memoId=${memoId}&listeId=${listeId}`);
	xhr.onload = function() {
		if (xhr.status === 200) {
			console.log("Mémo supprimé avec succès.");

			chargerMemo(listeId);
		} else {
			console.error("Failed to delete memo.");
		}
	};
	xhr.send();
}

function conversionMemos() {
	var convertToProductsBtn = document.getElementById('convertToProductsBtn');
	convertToProductsBtn.addEventListener('click', function() {
		var libelles = [];
		var lis = document.querySelectorAll('#modalMemo .modal-body p');
		lis.forEach(function(p) {
			var libelle = p.textContent.trim();
			// Ignorer les libellés vides
			if (libelle) {
				libelles.push(libelle);
			}
		});

		// Créer un formulaire dynamique
		var form = document.createElement('form');
		form.setAttribute('action', 'ServletConversionMemoProduit');
		form.setAttribute('method', 'GET');
		form.style.display = 'none'; // Ne pas afficher le formulaire sur la page

		// Créer un input pour chaque libellé
		libelles.forEach(function(libelle, index) {
			var input = document.createElement('input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('name', 'libelle_' + index);
			input.setAttribute('value', libelle);
			form.appendChild(input);
		});

		// Ajouter l'ID de la liste de courses

		var listeId = document.getElementById('listeIdInput').value;



		var listeIdInput = document.createElement('input');
		listeIdInput.setAttribute('type', 'hidden');
		listeIdInput.setAttribute('name', 'listeId');
		listeIdInput.setAttribute('value', id);
		form.appendChild(listeIdInput);

		document.body.appendChild(form);
		form.submit();
		form.remove();
	});
}


function ajouterInputLibelle() {
	//event.preventDefault();
	console.log("Clic pour ajouter une entrée reçu");

	var form = document.getElementById('addMemoForm');
	form.action = 'ServletGestionMemo?type_action=add_memo';
	form.method = 'post';
	form.submit();
	chargerMemo(listeId);

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

function toggleConvertButton(enable) {
		const convertToProductsBtn = document.getElementById('convertToProductsBtn');
		convertToProductsBtn.disabled = !enable;
	}

/** Appel de la fonction de fermeture sur le document */
$(document).ready(function() {
	forcerFermetureModal();

	
});
