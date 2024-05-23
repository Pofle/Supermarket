let products = [];

function creerCarteProduit(product) {
	const prixKilo = !isNaN(product.poids) ? `${(product.prix * 1000 / product.poids).toFixed(2)}€/kg` : '';

	const prixPromotion = !isNaN(Number(product.tauxPromotion)) ? (product.prix * (1 - Number(product.tauxPromotion) / 100)).toFixed(2) : null;
	const prixPromotionKilo = !isNaN(Number(product.tauxPromotion)) && !isNaN(product.poids) ?
		((prixPromotion * 1000 / product.poids)).toFixed(2) + '€/kg' : null;

	const cardHTML = `
 	<div class="card border rounded shadow-sm mb-3 custom-card">
        <div class="card-body p-3">
            <h6 class="card-title">${product.libelle} 
            ${prixPromotion ? `<span class="badge badge-danger ml-2">${Number(product.tauxPromotion).toFixed(2)}%</span>` : ''}
            </h6> 
            <p class="card-text">
                ${prixPromotion ? `<del>${product.prix.toFixed(2)}€</del>` : product.prix.toFixed(2)}€
                ${prixPromotion ? `<span class="font-weight-bold text-danger ml-2">${prixPromotion}€</span>` : ''}
                ${prixKilo && !prixPromotionKilo ? `<span class="badge badge-success ml-2 bg-secondary">${prixKilo}</span>` : ''}
                ${prixPromotionKilo ? `<span class="badge badge-success bg-secondary">${prixKilo ? `<del>${prixKilo}</del>` : ''} ${prixPromotionKilo}</span>` : ''}
            </p>
            <span class="badge badge-success">${product.conditionnement ? `<span>${product.conditionnement}</span>` : ''}</span> <small class="text-muted">Nutriscore: ${product.nutriscore}</small>
            ${product.categorie ? `<p class="card-text m-0"><small class="text-muted">Catégorie: ${product.categorie}</small></p>` : ''}
            ${product.rayon ? `<p class="card-text m-0"><small class="text-muted">Rayon: ${product.rayon}</small></p>` : ''}
        </div>
    </div>`;



	const tempDiv = document.createElement('div');
	tempDiv.innerHTML = cardHTML.trim();
	return tempDiv.firstChild;
}

function afficherProduits(products, containerId) {
	const container = document.getElementById(containerId);
	container.innerHTML = '';

	products.forEach(product => {
		const productCard = creerCarteProduit(product);
		const colDiv = document.createElement('div');
		colDiv.classList.add('col-md-3', 'mb-4');
		colDiv.innerHTML = `
		
            <div class="card shadow-sm custom-card">
            <a href=detailProduit?ean=${product.ean} class="product-link">
                <img src="/SupermarketG3/recupererImage?cheminImage=${encodeURIComponent(product.vignetteBase64)}" class="card-img-top rounded-top" alt="Image du produit">
                <div class="card-body">
                    ${productCard.innerHTML}
                </div>
                </a>
                <button class="btn btn-primary btn-sm ajouterpanier-bottom-card mt-auto" onclick="ajouterProduit('${product.ean}')">
            Ajouter au panier
        </button>
            </div>
            
        `;
		container.appendChild(colDiv);
	});
}

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
		const tauxPromotion = produit.querySelector('tauxPromotion')?.textContent;


		return { ean, vignetteBase64, prix, libelle, marque, conditionnement, poids, nutriscore, quantiteConditionnement, categorie, rayon, quantiteCommandee, tauxPromotion };
	});
}

document.addEventListener("DOMContentLoaded", function() {
	fetch('produits')
		.then(response => response.text())
		.then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
		.then(xml => {
			products = parseProductsFromXML(xml);
			afficherProduits(products, 'article-container');
		})
		.catch(error => console.error('Erreur:', error));

	const searchBar = document.getElementById('search-bar');
	const sortOptions = document.getElementById('sort-options');

	searchBar.addEventListener('input', handleFilterChange);
	sortOptions.addEventListener('change', handleFilterChange);

	function handleFilterChange() {
		const searchText = searchBar.value.trim().toLowerCase();
		const sortValue = sortOptions.value;

		let filteredProducts = products.filter(product => {
			return searchText === '' || product.libelle.toLowerCase().includes(searchText);
		});

		if (sortValue === 'prixKilo-asc') {
			filteredProducts.sort((a, b) => (a.prix * 1000 / a.poids) - (b.prix * 1000 / b.poids));
		} else if (sortValue === 'prixKilo-desc') {
			filteredProducts.sort((a, b) => (b.prix * 1000 / b.poids) - (a.prix * 1000 / a.poids));
		} else {
			filteredProducts.sort((a, b) => b.quantiteCommandee - a.quantiteCommandee);
		}

		afficherProduits(filteredProducts, 'article-container');
	}
});
