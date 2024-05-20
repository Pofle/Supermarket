products = [];

/**
 * Création d'une carte de produit et ajout dans le dom
 */
function createProductCard(product) {
	const articleCard = document.createElement('div');
	articleCard.classList.add('article-card');

	const link = document.createElement('a');
	link.href = `/SupermarketG3/detailProduit?ean=${product.ean}`;

	const productInfo = document.createElement('div');
	productInfo.classList.add('product-info');

	const productImage = document.createElement('div');
	productImage.classList.add('product-image');
	const img = document.createElement('img');
	img.src = `/SupermarketG3/recupererImage?cheminImage=${encodeURIComponent(product.vignetteBase64)}`;
	img.classList.add('image');
	productImage.appendChild(img);

	const productDetails = document.createElement('div');
	productDetails.classList.add('product-details');

	const price = document.createElement('p');
	price.classList.add('price');
	price.textContent = `${product.prix.toFixed(2)}€`;
	productDetails.appendChild(price);

	const libelleMarque = document.createElement('p');
	libelleMarque.classList.add('libelle-marque');
	libelleMarque.textContent = `${product.libelle} - ${product.marque}`;
	productDetails.appendChild(libelleMarque);

	const additionalInfo = document.createElement('p');
	additionalInfo.classList.add('additional-info');
	if (product.conditionnement) {
		additionalInfo.textContent = product.quantiteConditionnement + " " + product.conditionnement;
	} else {
		const prixKilo = (product.prix * 1000 / product.poids).toFixed(2);
		additionalInfo.textContent = `${product.poids}g - ${prixKilo}€/kg`;
	}
	productDetails.appendChild(additionalInfo);

	const nutriscoreP = document.createElement('p');
	nutriscoreP.classList.add('nutriscore');
	nutriscoreP.textContent = `Nutriscore: ${product.nutriscore}`;
	productDetails.appendChild(nutriscoreP);

	if (product.categorie) {
		const categorieP = document.createElement('p');
		categorieP.classList.add('categorie');
		categorieP.textContent = `Catégorie: ${product.categorie}`
		productDetails.appendChild(categorieP);
	}

	if (product.rayon) {
		const rayonP = document.createElement('p');
		rayonP.classList.add('rayon');
		rayonP.textContent = `Rayon: ${product.rayon}`;
		productDetails.appendChild(rayonP);
	}

	const boutonAjouterPanier = document.createElement("div");
	boutonAjouterPanier.innerHTML = svgCaddie;
	boutonAjouterPanier.className = 'ajouterpanier-bottom-card';
	console.log(svgCaddie);
	boutonAjouterPanier.addEventListener('click', () => {
		ajouterProduit(product.ean);
	})

	productInfo.appendChild(productImage);
	productInfo.appendChild(productDetails);

	link.appendChild(productInfo);
	articleCard.appendChild(link);
	articleCard.appendChild(boutonAjouterPanier);

	return articleCard;
}

/**
 * Remplace les produits déjà apparaissant afin de mettre les nouveaux produits retournés par le service
 */
function displayProducts(products, containerId, svg) {
	const container = document.getElementById(containerId);
	container.innerHTML = '';

	products.forEach(product => {
		const productCard = createProductCard(product, svg);
		container.appendChild(productCard);
	});
}

/**
 * Charger les produits à partir du XML retourné
 */
function parseProductsFromXML(xml) {
	const produits = xml.querySelectorAll('items');
	return Array.from(produits).map(produit => {
		const ean = produit.querySelector('ean').textContent;
		const vignetteBase64 = produit.querySelector('repertoireVignette').textContent;
		const prix = parseFloat(produit.querySelector('prix').textContent);
		const libelle = produit.querySelector(':scope > libelle').textContent;
		const marque = produit.querySelector('marque').textContent;
		const conditionnement = produit.querySelector('conditionnement')?.textContent;
		const poids = parseFloat(produit.querySelector('poids')?.textContent);
		const nutriscore = produit.querySelector('nutriscore').textContent;
		const quantiteConditionnement = produit.querySelector('quantiteConditionnement')?.textContent;
		const categorieElement = produit.querySelector('categorie');
		const categorie = categorieElement ? categorieElement.querySelector('libelle').textContent : null;
		const rayonElement = produit.querySelector('rayon');
		const rayon = rayonElement ? rayonElement.querySelector('libelle').textContent : null;
		const quantiteCommandee = produit.querySelector('quantiteCommandee').textContent;

		return { ean, vignetteBase64, prix, libelle, marque, conditionnement, poids, nutriscore, quantiteConditionnement, categorie, rayon, quantiteCommandee };
	});
}

function chargerSVG(url, callback) {
	fetch(url)
		.then(response => response.text())
		.then(svgText => {
			callback(svgText);
		})
}

document.addEventListener("DOMContentLoaded", function() {
	fetch('produits')
		.then(response => response.text())
		.then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
		.then(xml => {
			chargerSVG('recupererImage?cheminImage=caddie_ajout.svg', function(svgText) {
				svgCaddie = svgText;
				products = parseProductsFromXML(xml);
				displayProducts(products, 'article-container', svgCaddie);
			});
		})
		.catch(error => console.error('Erreur:', error));



	fetch('categories')
		.then(response => response.text())
		.then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
		.then(xml => {
			const categories = xml.querySelectorAll('items');
			const categorieSelect = document.getElementById('categorie-select');
			categories.forEach(categorie => {
				const libelle = categorie.querySelector('libelle').textContent;
				const option = document.createElement('option');
				option.value = libelle;
				option.textContent = libelle;
				categorieSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Erreur lors du chargement des catégories:', error));

	fetch('rayons')
		.then(response => response.text())
		.then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
		.then(xml => {
			const rayons = xml.querySelectorAll('items');
			const rayonSelect = document.getElementById('rayon-select');
			rayons.forEach(rayon => {
				const libelle = rayon.querySelector('libelle').textContent;
				const option = document.createElement('option');
				option.value = libelle;
				option.textContent = libelle;
				rayonSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Erreur lors du chargement des rayons:', error));

	const categorieSelect = document.getElementById('categorie-select');
	const rayonSelect = document.getElementById('rayon-select');
	const searchBar = document.getElementById('search-bar');

	categorieSelect.addEventListener('change', handleFilterChange);
	rayonSelect.addEventListener('change', handleFilterChange);
	searchBar.addEventListener('input', handleFilterChange);

	function handleFilterChange() {
		const selectedCategories = Array.from(categorieSelect.selectedOptions).map(option => option.value);
		const selectedRayons = Array.from(rayonSelect.selectedOptions).map(option => option.value);
		const searchText = searchBar.value.trim();

		const filteredProducts = products.filter(product => {
			if (selectedCategories.length > 0 && !selectedCategories.includes(product.categorie)) {
				return false;
			}
			if (selectedRayons.length > 0 && !selectedRayons.includes(product.rayon)) {
				return false;
			}
			if (searchText !== '' && !product.libelle.toLowerCase().includes(searchText)) {
				return false;
			}
			return true;
		});

		const sortedProducts = filteredProducts.sort((a, b) => b.quantiteCommandee - a.quantiteCommandee);

		displayProducts(sortedProducts, 'article-container');
	}
});
