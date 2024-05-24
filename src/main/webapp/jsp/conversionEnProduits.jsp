<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <jsp:include page="/jsp/header.jsp">
        <jsp:param name="title" value="Produits proposés" />
    </jsp:include>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container">
        <h1>Produits proposés</h1>
        <input type="hidden" id="listeId" value="${listeId}" />
        <div class="row">
            <c:forEach var="entry" items="${produitsMemos}">
                <h2>Memo: ${entry.key}</h2>
                <c:if test="${not empty entry.value}">
                    <c:forEach var="produit" items="${entry.value}">
                        <div class="col-md-3 mb-4">
                            <div class="card border rounded shadow-sm mb-3 custom-card">
                                <a href="detailProduit?ean=${produit.ean}" class="product-link">
                                    <img src="/SupermarketG3/recupererImage?cheminImage=${produit.repertoireImage}" class="card-img-top rounded-top" alt="Image du produit">
                                </a>
                                <div class="card-body p-3">
                                    <h6 class="card-title">${produit.libelle}</h6>
                                    <p class="card-text">${produit.prix}€
                                        <c:if test="${not empty produit.poids}">
                                            <span class="badge badge-success ml-2 bg-secondary">${(produit.prix * 1000 / produit.poids)}€/kg</span>
                                        </c:if>
                                    </p>
                                    <span class="badge badge-success"><span>${produit.conditionnement}</span>
                                        <small class="text-muted">Nutriscore: ${produit.nutriscore}</small>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input choisir-produit" type="radio" name="memo_${entry.key}" id="product_${produit.ean}" value="${produit.ean}" data-memo="${entry.key}">
                                    <label class="form-check-label" for="product_${produit.ean}">Choisir ce produit</label>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty entry.value}">
                    <p>Pas de produit trouvé pour ce mémo.</p>
                </c:if>
            </c:forEach>
        </div>
        <button type="button" class="btn btn-success" id="validerSelection">Valider la sélection</button>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const selectedProducts = new Map();

            document.querySelectorAll(".choisir-produit").forEach(radio => {
                radio.addEventListener("change", function() {
                    const productId = this.value;
                    const memoKey = this.dataset.memo;

                    selectedProducts.set(memoKey, productId);
                });
            });

            document.getElementById("validerSelection").addEventListener("click", function() {
                const memosWithProducts = Array.from(document.querySelectorAll("h2")).map(h2 => h2.textContent.split(': ')[1]);
                const memosWithSelection = Array.from(selectedProducts.keys());

                const missingSelections = memosWithProducts.filter(memo => {
                    const hasProducts = document.querySelector(`.choisir-produit[data-memo="${memo}"]`);
                    return hasProducts && !memosWithSelection.includes(memo);
                });

                if (missingSelections.length > 0) {
                    alert("Veuillez sélectionner un produit pour chaque mémo disponible.");
                    return;
                }

                const form = document.createElement("form");
                form.method = "post";
                form.action = "confirmationConversionMemo";

                selectedProducts.forEach((productId, memoKey) => {
                    const input = document.createElement("input");
                    input.type = "hidden";
                    input.name = "selectedProduct_" + memoKey;
                    input.value = productId;
                    form.appendChild(input);
                });

                const listeIdInput = document.createElement("input");
                listeIdInput.type = "hidden";
                listeIdInput.name = "listeId";
                listeIdInput.value = document.getElementById("listeId").value;
                form.appendChild(listeIdInput);

                document.body.appendChild(form);
                form.submit();
            });
        });
    </script>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="javascript/conversionEnProduit.js"></script>
</body>
</html>
