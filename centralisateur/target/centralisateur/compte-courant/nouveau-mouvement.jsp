<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.centralisateur.dto.CompteCourantDTO" %>
<%@ page import="com.example.centralisateur.dto.TypeMouvementCourantDTO" %>
<%
    List<CompteCourantDTO> comptes = (List<CompteCourantDTO>) request.getAttribute("comptes");
    List<TypeMouvementCourantDTO> typesMouvement = (List<TypeMouvementCourantDTO>) request.getAttribute("typesMouvement");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouveau Mouvement</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <!-- NAVBAR -->
    <nav class="navbar">
        <div class="navbar-brand">Banque Centralisateur</div>
        <div class="navbar-nav">
            <a href="${pageContext.request.contextPath}/compte-courant/comptes" class="nav-link">Comptes Courants</a>
            <a href="${pageContext.request.contextPath}/compte-courant/mouvements?action=nouveau" class="nav-link">Nouveau Mouvement</a>
            <a href="${pageContext.request.contextPath}/login?logout=true" class="nav-link">Déconnexion</a>
        </div>
    </nav>

    <div class="container" style="padding: 2rem 1rem;">
        <header class="fade-in" style="margin-bottom: 2rem;">
            <h1>Nouveau Mouvement</h1>
            <p>Création d'un nouveau mouvement de compte</p>
        </header>

        <!-- Messages d'erreur -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="card" style="background-color: #fbeaea; color: #d32f2f; margin-bottom: 1rem;">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <!-- Formulaire de création -->
        <div class="card fade-in">
            <div class="card-title">Créer un Nouveau Mouvement</div>
            <form action="${pageContext.request.contextPath}/compte-courant/mouvements" method="post" style="margin-top: 1rem;">
                <input type="hidden" name="action" value="creer">

                <div class="row">
                    <div class="col">
                        <label for="compteId">Compte:</label>
                        <select id="compteId" name="compteId" required>
                            <option value="">Sélectionnez un compte</option>
                            <% if (comptes != null) {
                                for (CompteCourantDTO compte : comptes) { %>
                                    <option value="<%= compte.getId() %>">
                                        <%= compte.getNumeroCompte() %> - Client <%= compte.getClient().getId() %> (Solde: <%= compte.getSolde() %> €)
                                    </option>
                                <% }
                            } %>
                        </select>
                    </div>

                    <div class="col">
                        <label for="typeMouvement">Type de mouvement:</label>
                        <select id="typeMouvement" name="typeMouvement" required>
                            <option value="">Sélectionnez un type</option>
                            <% if (typesMouvement != null) {
                                for (TypeMouvementCourantDTO type : typesMouvement) { %>
                                    <option value="<%= type.getId() %>">
                                        <%= type.getLibelle() %> (<%= type.getTypeSolde() > 0 ? "Crédit" : "Débit" %>)
                                    </option>
                                <% }
                            } %>
                        </select>
                    </div>
                </div>

                <div class="row" style="margin-top: 1rem;">
                    <div class="col">
                        <label for="montant">Montant (€):</label>
                        <input type="number" id="montant" name="montant" step="0.01" min="0.01" placeholder="0.00" required>
                    </div>
                </div>

                <div style="margin-top: 1rem;">
                    <button type="submit" class="btn btn-primary">Créer le mouvement</button>
                    <a href="${pageContext.request.contextPath}/compte-courant/comptes" class="btn" style="background: #6c757d; color: white; margin-left: 10px;">Annuler</a>
                </div>
            </form>
        </div>

        <!-- Section aide -->
        <div class="card-help fade-in" style="margin-top: 2rem;">
            <div class="card-title">Aide</div>
            <div style="padding-left: 1%">
                <p><strong>Types de mouvement disponibles:</strong></p>
                <ul style="padding-left: 3%">
                    <% if (typesMouvement != null) {
                        for (TypeMouvementCourantDTO type : typesMouvement) { %>
                            <li>
                                <strong><%= type.getLibelle() %>:</strong> 
                                <%= type.getTypeSolde() > 0 ? "Créditeur" : "Débiteur" %>
                            </li>
                    <% }
                    } %>
                </ul>
                <p class="card-alert">
                    <strong>Note:</strong> Les mouvements créés doivent être validés pour affecter le solde du compte.
                </p>
            </div>
        </div>

    </div>
</body>
</html>
