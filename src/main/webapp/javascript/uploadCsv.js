function uploadCSV() {
    var fileInput = document.getElementById('fileInput');
    var file = fileInput.files[0];

    var formData = new FormData();
    formData.append('file', file);

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'ajouterProduit', true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log('Le fichier a été envoyé et traité avec succès');
            displaySuccesMessage();
        } else {
            console.error('Une erreur est survenue lors du traitement du fichier');
        	displayErrorMessage(xhr.responseText);
        }
    };

	xhr.onerror = function() {
        console.error('Une erreur est survenue lors du traitement du fichier');
		displayErrorMessage(xhr.responseText);
	}


    xhr.send(formData);
}

function displayErrorMessage(message) {
	var errorMessageElement = document.getElementById('resultMessage');
	errorMessageElement.textContent = message;
	errorMessageElement.style.display = 'block';
	errorMessageElement.style.color = 'red';
}

function displaySuccesMessage() {
	var errorMessageElement = document.getElementById('resultMessage');
	errorMessageElement.textContent = "Le fichier a été traité avec succès";
	errorMessageElement.style.display = 'block';
	errorMessageElement.style.color = 'green';
}

document.getElementById('uploadButton').addEventListener('click', uploadCSV);