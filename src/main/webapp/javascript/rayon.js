function getCategoriesByRayonId(event, rayonId) {
	const xhr = new XMLHttpRequest();
	xhr.open('GET', `categories?rayonId=${rayonId}`);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onload = function() {
		if (xhr.status === 200) {
			resetAndHighlightRayon(event);
			const xmlResponse = xhr.responseXML;
			const categories = xmlResponse.getElementsByTagName('categorie');
			var categoriesHtml = '';
			for (let i = 0; i < categories.length; i++) {
				const categorieId = categories[i].getAttribute('id');
				const categorieLibelle = categories[i].textContent;
				categoriesHtml += `<li class='list-group-item categorie-item' data-categorie-id='${categorieId}'>${categorieLibelle}</li>`;
			}
			document.getElementById('categories').innerHTML = categoriesHtml;
			const categorieItems = document.querySelectorAll('.categorie-item');
			for (const categorieItem of categorieItems) {
				categorieItem.addEventListener('click', function(eventCategorie) {
					const categorieId = this.dataset.categorieId;
					getProduitsByCategorieId(eventCategorie, categorieId);
				});
			}
		} else {
			console.error('Erreur lors de la récupération des catégories pour le rayon:', xhr.statusText);
		}
	};
	xhr.send();
}

function getProduitsByCategorieId(event, categorieId) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `ajoutPanier?categorieId=${categorieId}`);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        if (xhr.status === 200) {
            resetAndHighlightCategory(event);
            const xmlResponse = xhr.responseXML;
            const produits = xmlResponse.getElementsByTagName('produit');
            var produitsHtml = '<div class="row">';
            for (let i = 0; i < produits.length; i++) {
                if (i > 0 && i % 3 === 0) {
                    produitsHtml += '</div><div class="row">';
                }
                const produitLibelle = produits[i].getElementsByTagName('libelle')[0].textContent;
                const produitPoids = produits[i].getElementsByTagName('poids')[0].textContent;
                const produitPrix = produits[i].getElementsByTagName('prix')[0].textContent;
                const produitConditionnement = produits[i].getElementsByTagName('conditionnement')[0].textContent;
                const produitImage = produits[i].getElementsByTagName('image')[0].textContent;
                const ean = produits[i]?.getAttribute('id');
                const tauxPromotion = produits[i].getElementsByTagName('tauxPromotion')[0]?.textContent;
                const nombreAchats = produits[i].getElementsByTagName('nombreAchats')[0]?.textContent

                const prixKilo = !isNaN(produitPoids) ? `${(produitPrix * 1000 / produitPoids).toFixed(2)}€/kg` : '';

                const prixPromotion = !isNaN(Number(tauxPromotion)) ? (produitPrix * (1 - Number(tauxPromotion) / 100)).toFixed(2) : null;
                const prixPromotionKilo = !isNaN(Number(tauxPromotion)) && !isNaN(produitPoids) ?
                    ((tauxPromotion * 1000 / produitPoids)).toFixed(2) + '€/kg' : null;

                const produitItemHtml = `
                    <div class="col-md-4">
                        <div class="card mb-3">
                            <div class="row no-gutters">
                                <a href="detailProduit?ean=${ean}" class="product-link">
                                    <div class="col-md-12">
                                        <img class="card-img" src="recupererImage?cheminImage=${produitImage}">
                                    </div>
                                    <div class="card-md-12">
                                        <div class="card-body">
                                            <h6 class="card-title">${produitLibelle} 
                                            ${prixPromotion ? `<span class="badge badge-danger ml-2">${Number(tauxPromotion).toFixed(2)}%</span>` : ''}
                                            </h6> 
                                            <p class="card-text">
                                                ${prixPromotion ? `<del>${Number(produitPrix).toFixed(2)}€</del>` : Number(produitPrix).toFixed(2)}€
                                                ${prixPromotion ? `<span class="font-weight-bold text-danger ml-2">${prixPromotion}€</span>` : ''}
                                                ${prixKilo && !prixPromotionKilo ? `<span class="badge badge-success ml-2 bg-secondary">${prixKilo}</span>` : ''}
                                                ${prixPromotionKilo ? `<span class="badge badge-success bg-secondary">${prixKilo ? `<del>${prixKilo}</del>` : ''} ${prixPromotionKilo}</span>` : ''}
                                            </p>
                                            <span class="badge badge-success">${produitConditionnement ? `<span>${produitConditionnement}</span>` : ''}</span>
                                        </div>
                                    </div>
                                </a>
                            </div>
                            <div class="card-footer">
                                <button class="btn btn-primary btn-sm ajouter-panier-btn" onclick="ajouterProduit(${ean})">
                                    Ajouter au panier
                                </button>
                            </div>
                        </div>
                    </div>
                `;

                produitsHtml += produitItemHtml;
            }
            produitsHtml += '</div>';
            document.getElementById('produits').innerHTML = produitsHtml;
        } else {
            console.error('Erreur lors de la récupération des produits pour le rayon:', xhr.statusText);
        }
    };
    xhr.send();
}

function resetAndHighlightRayon(event) {
	const rayonItems = document.querySelectorAll('.rayon-item');
	for (const rayonItem of rayonItems) {
		rayonItem.classList.remove('bg-primary', 'text-white');
	}
	event.target.classList.add('bg-primary', 'text-white');
	document.getElementById('categories').innerHTML = '';
	document.getElementById('produits').innerHTML = '';
}

function resetAndHighlightCategory(event) {
	const categorieItems = document.querySelectorAll('.categorie-item');
	for (const categorieItem of categorieItems) {
		categorieItem.classList.remove('bg-primary', 'text-white');
	}
	event.target.classList.add('bg-primary', 'text-white');
	document.getElementById('produits').innerHTML = '';
}

document.addEventListener('DOMContentLoaded', function() {
	const rayonItems = document.querySelectorAll('.rayon-item');
	for (const rayonItem of rayonItems) {
		rayonItem.addEventListener('click', function(event) {
			const rayonId = this.dataset.rayonId;
			getCategoriesByRayonId(event, rayonId);
		});
	}
});

