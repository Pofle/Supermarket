document.addEventListener("DOMContentLoaded", function() {
	function updatePanier() {
		const xhr = new XMLHttpRequest();
		xhr.open("GET", "ajoutPanier?displayOption=all", true);
		xhr.setRequestHeader("Content-Type", "application/xml");

		xhr.onload = function() {
			if (xhr.status === 200) {
				const responseXML = xhr.responseXML;
				const produits = responseXML.getElementsByTagName("produit");
				const totalPrix = responseXML.getElementsByTagName("prixTotal")[0].textContent;
				const panierContainer = document.querySelector(".produits-container");
				panierContainer.innerHTML = '';
				if (produits.length > 0) {
					const validateButton = document.getElementById("validerPanier");
					validateButton.style.display = 'block';
					const header = document.createElement("h1");
					header.textContent = "Votre panier";
					panierContainer.appendChild(header);

					Array.from(produits).forEach(produit => {
						const libelle = produit.getElementsByTagName("libelle")[0].textContent;
						const ean = produit.getElementsByTagName("ean")[0].textContent;
						const quantite = parseInt(produit.getElementsByTagName("quantite")[0].textContent);
						const prix = parseFloat(produit.getElementsByTagName("prix")[0].textContent);
						const conditionnement = produit.getElementsByTagName("conditionnement")[0]?.textContent;
						const poids = produit.getElementsByTagName("poids")[0]?.textContent;
						const promotion = produit.getElementsByTagName("promotion")[0]?.textContent;
						const image = produit.getElementsByTagName("imageLocation")[0]?.textContent;

						const prixTotal = prix * quantite;

						const produitDiv = document.createElement("div");
						produitDiv.classList.add("produit");

						const leftContainer = document.createElement("div");
						leftContainer.classList.add("left-container");
						const img = document.createElement("img");
						img.src = `recupererImage?cheminImage=${encodeURIComponent(image)}`;
						leftContainer.appendChild(img);

						const centerContainer = document.createElement("div");
						centerContainer.classList.add("center-container");

						const libelleDiv = document.createElement("div");
						libelleDiv.classList.add('libelle');
						libelleDiv.textContent = `${libelle}`;
						centerContainer.appendChild(libelleDiv);

						const infosDiv = document.createElement("div");
						infosDiv.classList.add("ean");
						infosDiv.textContent = `${prix.toFixed(2)}€ ${conditionnement}`;
						if (poids != null) {
							infosDiv.textContent += ` - ${(prix * 1000 / poids).toFixed(2)}€/kg`;
						}
						centerContainer.appendChild(infosDiv);

						const rightContainer = document.createElement("div");
						rightContainer.classList.add("right-container");

						const quantiteContainer = document.createElement("div");
						quantiteContainer.classList.add("quantite-container");

						const input = document.createElement("input");
						input.type = "text";
						input.classList.add("quantite-input");
						input.value = quantite;
						input.disabled = true

						const btnPlus = document.createElement("button");
						btnPlus.classList.add("btn");
						btnPlus.textContent = "+";
						btnPlus.addEventListener("click", function() {
							handleQuantiteChange(ean, 1);
						});

						const btnMoins = document.createElement("button");
						btnMoins.classList.add("btn");
						btnMoins.textContent = "-";
						btnMoins.addEventListener("click", function() {
							handleQuantiteChange(ean, -1);
						});

						const prixTotalContainer = document.createElement("div");
						prixTotalContainer.className = 'prixtotal-container';


						const prixTotalProduit = document.createElement("p");
						prixTotalProduit.textContent = `${prixTotal.toFixed(2)}€`;
						prixTotalProduit.className = "prixTotalPrd";
						prixTotalContainer.appendChild(prixTotalProduit);

						if (promotion != null) {
							const prixTotalProduitPromo = document.createElement("p");
							prixTotalProduit.className = "prixbarre";
							prixTotalProduitPromo.className = 'prixTotalPrd';
							prixTotalProduitPromo.textContent += `${(prixTotal.toFixed(2) * (1 - (Number(promotion) / 100))).toFixed(2)}€`;
							prixTotalContainer.appendChild(prixTotalProduitPromo);
							const promotionDiv = document.createElement("div");
							promotionDiv.classList.add('vignettepromotion');
							const promotionContent = document.createElement("p");
							promotionContent.textContent = `${Number(promotion)}% d'économies`;
							promotionDiv.appendChild(promotionContent)
							centerContainer.appendChild(promotionDiv);
						}

						quantiteContainer.appendChild(btnMoins);
						quantiteContainer.appendChild(input);
						quantiteContainer.appendChild(btnPlus);
						rightContainer.appendChild(quantiteContainer);
						rightContainer.appendChild(prixTotalContainer);
						produitDiv.appendChild(leftContainer);
						produitDiv.appendChild(centerContainer);
						produitDiv.appendChild(rightContainer);
						panierContainer.appendChild(produitDiv);
					});

					const totalHeader = document.getElementById("prixTotal");
					totalHeader.textContent = `Total: ${Number(totalPrix).toFixed(2)}€`;
					panierContainer.appendChild(totalDiv);
				} else {
					document.getElementById("resume-container").style.display = 'none';
					const emptyMessage = document.createElement("h1");
					emptyMessage.textContent = "Votre panier est vide...";
					panierContainer.appendChild(emptyMessage);
				}
			}
		};
		xhr.send();
	}

	function handleQuantiteChange(ean, change) {
		const xhr = new XMLHttpRequest();

		xhr.open("POST", "ajoutPanier", true);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		xhr.onload = function() {
			if (xhr.status === 200) {
				updatePanier();
			} else {
				console.error("Une erreur est survenue lors de la mise à jour de la quantité.");
			}
		};
		xhr.send(`ean=${ean}&quantite=${change}`);
	}

	updatePanier();
});

