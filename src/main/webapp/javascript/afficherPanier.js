document.addEventListener("DOMContentLoaded", function() {
	function updateProduct(ean, quantite, prix, promotion) {
		const produitDiv = document.querySelector(`#produit[data-ean="${ean}"]`);
		if (produitDiv) {
			if (quantite > 0) {
				const quantiteInput = produitDiv.querySelector(".quantite-input");
				quantiteInput.value = quantite;

				const prixTotalProduitPromo = produitDiv.querySelector("#prixTotalProduitPromo");

				const prixTotalProduit = produitDiv.querySelector("#prixTotalProduit");

				let prixTotal = prix * quantite;
				if (promotion) {
					prixTotalProduit.innerHTML = `<s class="text-muted">${prixTotal.toFixed(2)}\u20ac</s>`;
					prixTotalProduitPromo.textContent = `${(((prixTotal) * (1 - (Number(promotion) / 100))).toFixed(2))}€`;
				} else {
					prixTotalProduit.textContent = `${prixTotal.toFixed(2)}€`;
				}
			} else {
				produitDiv.remove();
			}
		}
	}

	function handleQuantiteChange(ean, change) {
		const xhr = new XMLHttpRequest();
		xhr.open("POST", "ajoutPanier", true);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		xhr.onload = function() {
			if (xhr.status === 200) {
				console.log(xhr.responseText)
				const responseXML = xhr.responseXML;
				const produit = responseXML.querySelector("produit");
				const totalPrix = responseXML.getElementsByTagName("prixTotal")[0].textContent;

				if (produit) {
					const quantite = parseInt(produit.getElementsByTagName("quantite")[0].textContent);
					const prix = parseFloat(produit.getElementsByTagName("prix")[0].textContent);
					const promotion = produit.getElementsByTagName("promotion")[0]?.textContent;
					updateProduct(ean, quantite, prix, promotion);
				}
				updateTotalPrix(totalPrix);
			} else {
				console.error("Une erreur est survenue lors de la mise à jour de la quantité.");
			}
		};
		xhr.send(`ean=${ean}&quantite=${change}`);
	}

	function updateTotalPrix(totalPrix) {
		const totalHeader = document.getElementById("prixTotal");
		totalHeader.textContent = `Total: ${Number(totalPrix).toFixed(2)}\u20ac`;
		if (totalPrix == 0) {
			const resumeContainer = document.getElementById("resume-container");
			resumeContainer.style.display = 'none';

			const emptyMessage = document.getElementById("panier-title");
			emptyMessage.textContent = "Votre panier est vide...";
		} else {
			const resumeContainer = document.getElementById("resume-container");
			resumeContainer.style.display = 'block';
		}
	}

	const pointsUtilisesInput = document.getElementById("pointsUtilises");
	if (pointsUtilisesInput) {
		const maxPoints = pointsUtilisesInput.getAttribute("max");
		const ptsParEuro = 10;


		pointsUtilisesInput.addEventListener("blur", function() {
			const valeurInput = this.value.replace(/\D/g, "");
			this.value = valeurInput;
			const pointsAjustes = Math.floor(parseInt(valeurInput) / ptsParEuro) * ptsParEuro;

			if (parseInt(pointsAjustes) > parseInt(maxPoints)) {
				this.value = Math.floor(parseInt(maxPoints) / ptsParEuro) * ptsParEuro;
			} else {
				this.value = pointsAjustes;
			}
			const reduction = this.value / 10;
			//document.getElementById("prixTotal").textContent = `Total: ${nouveauPrix.toFixed(2)}€`;
			if (this.value == 0) {
				document.getElementById("reductionPoints").style.display = 'none';
			} else {
				document.getElementById("reductionPoints").style.display = 'block';
				document.getElementById("reductionPoints").textContent = `Réduction appliquée (${this.value}pts): -${reduction.toFixed(2)}€`;
			}

			var pointsUtilises = document.getElementById("pointsUtilises").value;
			document.getElementById("pointsUtilisesInput").value = pointsUtilises;
		});
	}
	
	document.querySelectorAll(".btn-plus").forEach(button => {
		button.addEventListener("click", function() {
			const ean = this.dataset.ean;
			handleQuantiteChange(ean, 1);
		});
	});

	document.querySelectorAll(".btn-moins").forEach(button => {
		button.addEventListener('click', function() {

			const ean = this.dataset.ean;
			handleQuantiteChange(ean, -1);
		});
	});
});
