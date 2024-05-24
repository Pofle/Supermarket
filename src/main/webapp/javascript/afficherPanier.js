var processedProductIds = new Set();

document.addEventListener("DOMContentLoaded", function() {
    function calculateTotalPrice() {
        let totalPrice = 0;
        document.querySelectorAll("#produit").forEach(produitDiv => {
            const quantiteInput = produitDiv.querySelector(".quantite-input");
            if (quantiteInput) {
                const quantite = parseInt(quantiteInput.value);
                const prixText = produitDiv.querySelector(".card-text").textContent;
                const prixMatch = prixText.match(/(\d+,\d+|\d+\.\d+)/);
                if (prixMatch) {
                    const prix = parseFloat(prixMatch[0].replace(",", "."));
                    const promotionBadge = produitDiv.querySelector(".badge-success");
                    const promotion = promotionBadge ? parseFloat(promotionBadge.textContent.replace("% d'économies", "")) : 0;
                    let prixTotal = prix * quantite;
                    if (promotion) {
                        prixTotal *= (1 - (promotion / 100));
                    }
                    totalPrice += prixTotal;
                }
            }
        });
        updateTotalPrix(totalPrice.toFixed(2));
    }

    function updateProduct(ean, quantite, prix, promotion, totalPrix) {
        const produitDiv = document.querySelector(`#produit[data-ean="${ean}"]`);
        if (!produitDiv) return;

        const quantiteInput = produitDiv.querySelector(".quantite-input");
        quantiteInput.value = quantite;

        const prixTotalProduitPromo = produitDiv.querySelector("#prixTotalProduitPromo");
        const prixTotalProduit = produitDiv.querySelector("#prixTotalProduit");

        let prixTotal = prix * quantite;
        if (promotion) {
            prixTotalProduit.innerHTML = `<s class="text-muted">${prixTotal.toFixed(2)}€</s>`;
            prixTotalProduitPromo.textContent = `${(prixTotal * (1 - (Number(promotion) / 100))).toFixed(2)}€`;
        } else {
            prixTotalProduit.textContent = `${prixTotal.toFixed(2)}€`;
        }

        if (quantite <= 0) {
            produitDiv.remove();
        }

        calculateTotalPrice();
    }

    function handleQuantiteChange(ean, change) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "ajoutPanier", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onload = function() {
            if (xhr.status === 200) {
                const responseXML = xhr.responseXML;
                const produit = responseXML.querySelector("produit");
                if (!produit) return;

                const quantite = parseInt(produit.querySelector("quantite").textContent);
                const prix = parseFloat(produit.querySelector("prix").textContent);
                const promotion = produit.querySelector("promotion")?.textContent;
                updateProduct(ean, quantite, prix, promotion, responseXML.querySelector("prixTotal").textContent);
            } else {
                console.error("Une erreur est survenue lors de la mise à jour de la quantité.");
            }
        };
        xhr.send(`ean=${ean}&quantite=${change}`);
    }

    function updateTotalPrix(totalPrix) {
        const totalHeader = document.getElementById("prixTotal");
        totalHeader.textContent = `${Number(totalPrix).toFixed(2)}€`;

        const resumeContainer = document.getElementById("resume-container");
        resumeContainer.style.display = totalPrix == 0 ? 'none' : 'block';

        const emptyMessage = document.getElementById("panier-title");
        emptyMessage.textContent = totalPrix == 0 ? "Votre panier est vide..." : "Votre panier";
    }

    const pointsUtilisesInput = document.getElementById("pointsUtilises");
    if (pointsUtilisesInput) {
        const maxPoints = pointsUtilisesInput.getAttribute("max");
        const ptsParEuro = 10;

        pointsUtilisesInput.addEventListener("blur", function() {
            let valeurInput = this.value.replace(/\D/g, "");
            valeurInput = Math.min(parseInt(valeurInput), parseInt(maxPoints)) || 0;
            this.value = Math.floor(valeurInput / ptsParEuro) * ptsParEuro;

            const reduction = this.value / 10;
            const reductionPoints = document.getElementById("reductionPoints");
            reductionPoints.style.display = this.value == 0 ? 'none' : 'block';
            reductionPoints.textContent = this.value == 0 ? "" : `Réduction appliquée (${this.value}pts): -${reduction.toFixed(2)}€`;

            document.getElementById("pointsUtilisesInput").value = this.value;
            calculateTotalPrice();
        });
    }

    function attachQuantiteHandlers() {
        document.querySelectorAll(".btn-plus, .btn-moins").forEach(button => {
            button.addEventListener("click", function() {
                const ean = this.dataset.ean;
                const change = this.classList.contains("btn-plus") ? 1 : -1;
                handleQuantiteChange(ean, change);
            });
        });

        document.querySelectorAll("#retirerProduitPanier").forEach(button => {
            button.addEventListener("click", function() {
                const ean = this.dataset.ean;
                console.log(ean);
                supprimerProduit(ean);
            });
        });
    }

    attachQuantiteHandlers();

    function afficherPopupProduitsRemplacement(produitsRemplacement, eanARemplacer) {
        var html = "<h5 class=\"card-title\">Aucun produit de remplacement disponible n'a été trouvé pour cet article</h5>";
        if (produitsRemplacement.length != 0) {
            html = `${Array.from(produitsRemplacement).map(produitRemplacement => `
                                    <div id="produit" class="card mb-3" data-ean="${eanARemplacer}">
                                    <div class="row no-gutters">
                                        <div class="col-md-4">
                                            <img src="recupererImage?cheminImage=${produitRemplacement.querySelector("image").textContent}" class="card-img" alt="">
                                        </div>
                                        <div class="col-md-8">
                                            <div class="card-body">
                                                <h5 class="card-title">${produitRemplacement.querySelector("libelle").textContent}</h5>
                                                <p class="card-text">${produitRemplacement.querySelector("conditionnement").textContent}</p>
                                                <p class="card-text">${Number(produitRemplacement.querySelector("prix").textContent).toFixed(2)} €</p>
                                                <div class="mt-3">
                                                    <p id="prixTotalProduit" class="font-weight-bold">
                                                        ${Number(produitRemplacement.querySelector("prix").textContent).toFixed(2)}
                                                    </p>
                                                </div>
                                                <button type="button" class="btn btn-primary choisir-produit" data-ean="${produitRemplacement.getAttribute("id")}">Choisir</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                `).join('')}`
        }

        const popup = document.createElement("div");
        popup.className = "modal fade";
        popup.innerHTML = `
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Produits de remplacement</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="card-deck">
                                            ${html}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `;
        document.body.appendChild(popup);
        $(popup).modal('show');

        popup.querySelectorAll(".choisir-produit").forEach(button => {
            button.addEventListener("click", function() {
                const ean = this.dataset.ean;
                const cardPanier = document.querySelector(`#produit[data-ean="${eanARemplacer}"]`);
                if (cardPanier) {
                    const produitRemplacement = Array.from(produitsRemplacement).find(produit => produit.getAttribute("id") === this.dataset.ean);
                    if (produitRemplacement) {
                        const imageSrc = produitRemplacement.querySelector("image").textContent;
                        const libelle = produitRemplacement.querySelector("libelle").textContent;
                        const conditionnement = produitRemplacement.querySelector("conditionnement").textContent;
                        const prix = produitRemplacement.querySelector("prix").textContent;

                        const newProductId = 'produit';

                        const tauxPromotion = produitRemplacement.querySelector("tauxPromotion") ? parseFloat(produitRemplacement.querySelector("tauxPromotion").textContent) : 0;
                        const newProductDiv = document.createElement("div");
                        newProductDiv.className = "card mb-3";
                        newProductDiv.setAttribute("data-ean", ean);
                        newProductDiv.id = newProductId;
                        newProductDiv.innerHTML = `
                            <div class="row no-gutters">
                                <div class="col-md-4">
                                    <img src="recupererImage?cheminImage=${imageSrc}" class="card-img" alt="">
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <h5 class="card-title">${libelle}</h5>
                                        <p class="card-text">${Number(prix).toFixed(2)} € ${conditionnement}
                                            ${produitRemplacement.querySelector("poids") ? ` - ${(prix * 1000 / produitRemplacement.querySelector("poids").textContent).toFixed(2)}€/kg` : ''}
                                        </p>
                                        ${tauxPromotion ? `<div class="badge badge-success">${tauxPromotion}% d'économies</div>` : ''}
                                        <div class="mt-3">
                                            <div class="btn-group" role="group">
                                                <button class="btn btn-outline-secondary btn-moins" data-ean="${ean}">-</button>
                                                <input type="text" class="form-control text-center quantite-input" value="1" disabled style="width: 50px;">
                                                <button class="btn btn-outline-secondary btn-plus" data-ean="${ean}">+</button>
                                            </div>
                                        </div>
                                        <div class="mt-3">
                                            ${tauxPromotion ? `
                                            <p id="prixTotalProduit" class="text-muted">
                                                <s>${(prix * 1).toFixed(2)}€</s>
                                            </p>
                                            <p id="prixTotalProduitPromo" class="font-weight-bold">
                                                ${(prix * (1 - tauxPromotion / 100)).toFixed(2)}€
                                            </p>
                                            ` : `
                                            <p id="prixTotalProduit" class="font-weight-bold">
                                                ${(prix * 1).toFixed(2)}€
                                            </p>`}
                                        </div>
                                        <div class="mt-3 text-right">
                                            <button id="retirerProduitPanier" type="button" class="btn btn-danger btn-block" data-ean="${ean}">Retirer produit du panier</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `;

                        // Ajouter gestionnaire d'événement pour le bouton de suppression
                        newProductDiv.querySelector("#retirerProduitPanier").addEventListener("click", function() {
                            supprimerProduit(ean);
                            newProductDiv.remove();
                        });

                        // Remplacer l'ancien produit par le nouveau
                        cardPanier.parentNode.replaceChild(newProductDiv, cardPanier);

                        var xhr = new XMLHttpRequest();
                        xhr.open('POST', 'ajoutPanier', true);
                        var params = `ean=${ean}`;
                        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                        xhr.onload = function() {
                            if (xhr.status === 200) {
                                calculateTotalPrice();
                            } else {
                                console.error('Une erreur est survenue lors du traitement de la session');
                            }
                        };
                        xhr.send(params);

                        var xhr = new XMLHttpRequest();
                        xhr.open('DELETE', `ajoutPanier?ean=${eanARemplacer}`, true);
                        var params = `ean=${eanARemplacer}`;
                        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                        xhr.onload = function() {
                            if (xhr.status === 200) {
                                calculateTotalPrice();
                            } else {
                                console.error('Une erreur est survenue lors du traitement de la session');
                            }
                        };
                        xhr.send(params);

                        // Réattacher les gestionnaires d'événements aux boutons de quantité des nouveaux produits
                        attachQuantiteHandlers();
                    }
                }
                $(popup).modal('hide');
            });
        });
    }

    function supprimerProduit(ean) {
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', `ajoutPanier?ean=${ean}`, true);
        var params = `ean=${ean}`;
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
            if (xhr.status === 200) {
                document.querySelector(`#produit[data-ean="${ean}"]`).remove();
                calculateTotalPrice();
            } else {
                console.error('Une erreur est survenue lors du traitement de la session');
            }
        };
        xhr.send(params);
    }

    function validerPanier() {
        const date = document.getElementById('date').value;
        const horaire = document.getElementById('horaire').value;
        const magasin = document.getElementById('magasin').value;

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "panier", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onload = function() {
            if (xhr.status === 200) {
                alert("Votre commande a bien été enregistrée");
                location.reload();
            } else {
                const responseXML = xhr.responseXML;
                if (!responseXML) {
                    alert(xhr.responseText);
                    return;
                } else {
                    $('#exampleModal').modal('hide');
                    alert("Des articles ne sont pas disponibles pour ce magasin à cette date. Sélectionnez des produits de remplacement.");
                }

                const produitsNonDisponibles = responseXML.getElementsByTagName("produit");
                if (produitsNonDisponibles.length > 0) {
                    for (let i = 0; i < produitsNonDisponibles.length; i++) {
                        const produit = produitsNonDisponibles[i];
                        const ean = produit.getAttribute("id");
                        const produitDiv = document.querySelector(`#produit[data-ean="${ean}"]`);
                        if (produitDiv) {
                            produitDiv.style.border = "4px solid #007bff";

                            const libelleElement = produitDiv.querySelector(".card-title");
                            if (libelleElement) {
                                const outOfStockSpan = document.createElement("span");
                                outOfStockSpan.textContent = " (Rupture de stock)";
                                outOfStockSpan.style.color = "red";
                                libelleElement.appendChild(outOfStockSpan);
                            }

                            if (!processedProductIds.has(ean)) {
                                processedProductIds.add(ean);
                                produitDiv.addEventListener("click", function() {
                                    const produitsRemplacement = produit.getElementsByTagName("produitRemplacement");
                                    const eanARemplacer = this.dataset.ean;
                                    afficherPopupProduitsRemplacement(produitsRemplacement, eanARemplacer);
                                });
                            } else {
                                return;
                            }
                        }
                    }
                }
            }
        };
        xhr.send(`magasin=${magasin}&date=${date}&horaire=${horaire}`);
    }

    document.getElementById("validerPanierFinal").addEventListener('click', validerPanier);
});
