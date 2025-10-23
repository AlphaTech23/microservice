// Exemple fetch vers API centralisateur
fetch('localhost:8080/api/central')
    .then(response => response.text())
    .then(data => {
        document.getElementById('result').innerText = data;
    });
