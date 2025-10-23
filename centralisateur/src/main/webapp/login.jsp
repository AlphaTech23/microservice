<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion - Centralisateur Banque</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>

<div class="login-wrapper">
    <div class="login-card">
        <div class="login-header">
            <h2>Centralisateur Banque</h2>
            <p>Connexion à votre compte</p>
        </div>
        <div class="login-body">
            <form method="post" action="login">
                <label for="login">Identifiant</label>
                <input type="text" id="login" name="login" required placeholder="Entrez votre identifiant">

                <label for="password">Mot de passe</label>
                <input type="password" id="password" name="password" required placeholder="Entrez votre mot de passe">

                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>

                <button type="submit">Se connecter</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
