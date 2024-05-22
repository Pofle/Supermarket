const panier = new Map()
function chargerProduits() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200) {
			traiterReponse(this);
		}
	}
	xhttp.open("GET", "recupererProduits", true);
	xhttp.send();
}

function traiterReponse(xml) {
	var xmlDoc = xml.responseXML;
	var produits = xmlDoc.getElementsByTagName("items");
	var carteContainer = document.getElementById("article-container");
	
	for(var i = 0; i < produits.length; i++) {
		var produit = produits[i];
		var descriptionCourte = produit.getElementsByTagName("descriptionCourte")[0].childNodes[0].nodeValue;
		var marque = produit.getElementsByTagName("marque")[0].childNodes[0].nodeValue;
		var ean = produit.getElementsByTagName("ean")[0].childNodes[0].nodeValue;
	
		var carteHTML = '<div class="article-card">';
		carteHTML += '<h2>' + descriptionCourte + '</h2>';
		carteHTML += '<p>Marque: ' + marque + '</p>';
		carteHTML += '<div><input type="number" id="'+ean+'" name="qte" min="0" value="0"><button onclick="ajouterProduit('+ean+')">Ajouter au panier</button></div>'
		carteHTML += '</div>';
		
		// Ajout de la carte au conteneur
        carteContainer.innerHTML += carteHTML;
	}
}

// Appel de la fonction pour charger les produits lors du chargement de la page
window.onload = function() {
    chargerProduits();
};