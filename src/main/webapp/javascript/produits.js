function ajouterProduit(x) {
	var valueAdded = parseInt(document.getElementById(x).value)
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'ajoutPanier', true);
	console.log(x);
	console.log(valueAdded)
	xhr.onload = function() {
		if (xhr.status === 200) {
			console.log('Le fichier a été envoyé et traité avec succès');
		} else {
			console.error('Une erreur est survenue lors du traitement du fichier');
		}
	};
	xhr.onerror = function() {
		console.error('Une erreur est survenue lors du traitement du fichier');

	}
	//var parametre = { "ean": x, "quantiteProduit": valueAdded }
	var parametre = "ean="+x+"&quantiteProduit="+valueAdded
	console.log(parametre)
	xhr.send(parametre);
}