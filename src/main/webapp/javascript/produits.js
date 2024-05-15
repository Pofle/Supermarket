function chargerProduits() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
<<<<<<< Updated upstream
		if(this.readyState == 4 && this.status == 200) {
			traiterReponse(this);
=======
		if (this.readyState == 4 && this.status == 200) {
			traiterProduits(this);
>>>>>>> Stashed changes
		}
	}
	xhttp.open("GET", "recupererProduits", true);
	xhttp.send();
}

<<<<<<< Updated upstream
function traiterReponse(xml) {
	var xmlDoc = xml.responseXML;
	var produits = xmlDoc.getElementsByTagName("items");
	var carteContainer = document.getElementById("article-container");
	
	for(var i = 0; i < produits.length; i++) {
		var produit = produits[i];
		var descriptionCourte = produit.getElementsByTagName("descriptionCourte")[0].childNodes[0].nodeValue;
		var marque = produit.getElementsByTagName("marque")[0].childNodes[0].nodeValue;
	
		var carteHTML = '<div class="article-card">';
		carteHTML += '<h2>' + descriptionCourte + '</h2>';
		carteHTML += '<p>Marque: ' + marque + '</p>';
		carteHTML += '</div>';
		
		// Ajout de la carte au conteneur
        carteContainer.innerHTML += carteHTML;
	}
}

// Appel de la fonction pour charger les produits lors du chargement de la page
window.onload = function() {
    chargerProduits();
=======
function traiterProduits(xml) {
	var xmlDoc = xml.responseXML;
	var produits = xmlDoc.getElementsByTagName("items");
	var carteContainer = document.getElementById("article-container");
	for (var i = 0; i < produits.length; i++) {
		var xmlProduit = produits[i];
		var produit = getProduitInformations(xmlProduit);
		// Ajout de la carte au conteneur
		carteContainer.append(createArticleCard(produit));
	}
}

function createArticleCard(produit) {
	var carteHTMLDiv = document.createElement('div');
	carteHTMLDiv.className = "article-card";
	
	const img = document.createElement("img");
	img.src = window.location.protocol + "//" + window.location.host +'/SupermarketG3/recupererImage?cheminImage='+encodeURIComponent(produit.vignette);
	img.className = 'image';
	carteHTMLDiv.append(img);
	
	var libelleProduit = document.createElement('h2');
	libelleProduit.innerText = produit.libelle;
	
	var marque = document.createElement('p');
	marque.innerText = 'Marque | ' + produit.marque;
	
	var prixUnitaire = document.createElement('p');
	prixUnitaire.innerText = 'Prix | ' + produit.prix_unitaire;
	
	var nutriScore = document.createElement('p');
	nutriScore.innerText = 'Nutriscore | ' + produit.nutriscore;
	
	
	if (isNotNullOrEmpty(produit.conditionnement)) {
		var conditionnementParagraph = document.createElement('p');
		conditionnementParagraph.innerText = 'Conditionnement | ' + produit.conditionnement;
		carteHTMLDiv.append(libelleProduit, marque, prixUnitaire, nutriScore, conditionnementParagraph);
	} else {
		var prix_kilo = (produit.prix_unitaire * 1000) / produit.poids;
		
		var poidsParagraph = document.createElement('p');
		poidsParagraph.innerText = 'Poids | ' + produit.prix_unitaire;
		
		var prixKiloParagraph = document.createElement('p');
		prixKiloParagraph.innerText = 'Prix au kilo | ' + prix_kilo.toFixed(2);
		carteHTMLDiv.append(libelleProduit, marque, prixUnitaire, nutriScore, poidsParagraph, prixKiloParagraph);
	}
	return carteHTMLDiv;
}

function getProduitInformations(xmlProduit, produit) {
	var produit = {};
	produit.marque = xmlProduit.getElementsByTagName("marque")[0].childNodes[0].nodeValue;
	produit.prix_unitaire = xmlProduit.getElementsByTagName("prix")[0].childNodes[0].nodeValue;
	produit.libelle = xmlProduit.getElementsByTagName("libelle")[0].childNodes[0].nodeValue;
	produit.nutriscore = xmlProduit.getElementsByTagName("nutriscore")[0].childNodes[0].nodeValue;
	produit.vignette = xmlProduit.getElementsByTagName("repertoireVignette")[0].childNodes[0].nodeValue;
	
	

	// On regarde si pour l'article, un poids correspondant existe.
	var poids = xmlProduit.getElementsByTagName("poids")[0];
	if (isNotNullOrEmpty(poids)) {
		produit.poids = poids.childNodes[0].nodeValue;;
	}

	// On regarde si pour l'article, un conditionnement correspondant existe.
	var conditionnement = xmlProduit.getElementsByTagName("conditionnement")[0];
	if (isNotNullOrEmpty(conditionnement)) {
		produit.conditionnement = conditionnement.childNodes[0].nodeValue;;
	}
	return produit;
}

function isNotNullOrEmpty(value) {
	return value !== null && typeof value !== 'undefined' && value !== '';
}

// Appel de la fonction pour charger les produits lors du chargement de la page
window.onload = function() {
	chargerProduits();
>>>>>>> Stashed changes
};