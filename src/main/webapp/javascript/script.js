/**
 * Script pour afficher les liste de courses
 */
document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('.btn-Add-IntoList').addEventListener('click', function () {
        fetch('/GestionArticleListe')
            .then(response => response.json())
            .then(data => {
                const selectList = document.getElementById('select_list');
                selectList.innerHTML = '<option value=""> -- Choisir -- </option>';
                data.forEach(list => {
                    const option = document.createElement('option');
                    option.value = list.id;
                    option.textContent = list.name;
                    selectList.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching shopping lists:', error));
    });
});