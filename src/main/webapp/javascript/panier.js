var compteurPanier = document.getElementById("panier-compteur");
var xhr = new XMLHttpRequest();
xhr.open('GET', 'ajoutPanier', true);
	
xhr.onload = function() {
	if (xhr.status === 200) {
		const produits = xhr.responseXML.querySelectorAll('nombreProduits');
		if(produits[0].textContent != '0') {
			compteurPanier.textContent = produits[0].textContent;
			compteurPanier.style.display = 'block';
			return;
		}
	} else {
		console.error('Une erreur est survenue lors du traitement de la session');
	}
	compteurPanier.style.display = 'none';
};
xhr.send();

function ajouterProduit(ean) {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'ajoutPanier', true);
	
	var params = `ean=${ean}`;
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	
	xhr.onload = function() {
		if (xhr.status === 200) {
			const produits = xhr.responseXML.querySelectorAll('nombreProduits');
			compteurPanier.textContent = produits[0].textContent;
			compteurPanier.style.display = 'block';
		} else {
			console.error('Une erreur est survenue lors du traitement de la session');
		}
	};
	xhr.send(params);
}