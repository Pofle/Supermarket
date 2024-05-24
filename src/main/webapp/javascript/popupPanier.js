function showPopup() {
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
}

function hidePopup() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

function keepCart() {
    console.log("Garder le précédent panier");
    validerChoix(true);
    hidePopup();
}

function retrieveCart() {
    console.log("Récupérer le panier associé à mon compte");
    validerChoix(false);
    hidePopup();
}

function validerChoix(choix) {
    const url = "gestionChoixPanier";
    const params = `choice=${choix ? 'garder' : 'remplacer'}`;
    
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Traitement du choix effectué !");
        }
    };
    xhr.send(params);
}

document.addEventListener("DOMContentLoaded", function() {
	var showPopupp = document.getElementById("popup-data").getAttribute("data-show-popup");
    if (showPopupp === "true") {
        showPopup();
    }
});

