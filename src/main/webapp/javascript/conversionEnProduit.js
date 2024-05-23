/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {
    const xmlString = document.getElementById("xmlData").textContent;
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(xmlString, "application/xml");

    const products = xmlDoc.getElementsByTagName("produit");
    const container = document.getElementById("productContainer");

    for (let i = 0; i < products.length; i++) {
        const produit = products[i];
        const libelle = produit.getElementsByTagName("libelle")[0].textContent;
        const marque = produit.getElementsByTagName("marque")[0].textContent;
        const descriptionCourte = produit.getElementsByTagName("descriptionCourte")[0].textContent;
        const nutriscore = produit.getElementsByTagName("nutriscore")[0].textContent;
        const prix = produit.getElementsByTagName("prix")[0].textContent;
        const imagelocation = produit.getElementsByTagName("imageLocation")[0].textContent;

        const card = document.createElement("div");
        card.className = "card";
        card.style.width = "18rem";

        const img = document.createElement("img");
        img.className = "card-img-top";
        img.src = `recupererImage?cheminImage=${imagelocation}`;
        card.appendChild(img);

        const cardBody = document.createElement("div");
        cardBody.className = "card-body";

        const cardTitle = document.createElement("h5");
        cardTitle.className = "card-title";
        cardTitle.textContent = libelle;
        cardBody.appendChild(cardTitle);

        const cardText = document.createElement("p");
        cardText.className = "card-text";
        cardText.textContent = descriptionCourte;
        cardBody.appendChild(cardText);

        const price = document.createElement("p");
        price.textContent = "Prix: " + prix;
        cardBody.appendChild(price);

        const brand = document.createElement("p");
        brand.textContent = "Marque: " + marque;
        cardBody.appendChild(brand);

        const score = document.createElement("p");
        score.textContent = "Nutriscore: " + nutriscore;
        cardBody.appendChild(score);

        const btn = document.createElement("a");
        btn.className = "btn btn-primary";
        btn.href = "#";
        btn.textContent = "Go somewhere";
        cardBody.appendChild(btn);

        card.appendChild(cardBody);
        container.appendChild(card);
    }
});