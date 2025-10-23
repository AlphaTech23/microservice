<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.centralisateur.dto.MouvementCourantDTO" %>
<%@ page import="com.example.centralisateur.dto.CompteCourantDTO" %>
<%@ page import="com.example.centralisateur.dto.TypeMouvementCourantDTO" %>
<%
    CompteCourantDTO compte = (CompteCourantDTO) request.getAttribute("compte");
    List<MouvementCourantDTO> mouvements = (List<MouvementCourantDTO>) request.getAttribute("mouvements");
    List<TypeMouvementCourantDTO> typesMouvement = (List<TypeMouvementCourantDTO>) request.getAttribute("typesMouvement");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mouvements du Compte</title>
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
            <h1>Mouvements du Compte</h1>
            <p>Gestion des mouvements et validation</p>
        </header>

        <!-- Messages -->
        <% if (session.getAttribute("success") != null) { %>
            <div class="card" style="background-color: #e6f4ea; color: #2e7d32; margin-bottom: 1rem;">
                <%= session.getAttribute("success") %>
            </div>
            <% session.removeAttribute("success"); %>
        <% } %>
        <% if (session.getAttribute("error") != null) { %>
            <div class="card" style="background-color: #fbeaea; color: #d32f2f; margin-bottom: 1rem;">
                <%= session.getAttribute("error") %>
            </div>
            <% session.removeAttribute("error"); %>
        <% } %>

        <% if (compte != null) { %>
            <!-- Informations du compte -->
            <div class="card fade-in">
                <div class="card-title">Informations du Compte</div>
                <div class="row">
                    <div class="col">
                        <p><strong>Numéro de compte:</strong> <%= compte.getNumeroCompte() %></p>
                        <p><strong>Client ID:</strong> <%= compte.getClient().getId() %></p>
                        <p><strong>Nom Client:</strong> <%= compte.getClient().getNom() %></p>
                        <p><strong>Date d'ouverture:</strong> <%= compte.getDateOuverture() %></p>
                    </div>
                    <div class="col">
                        <p><strong>Solde actuel:</strong> 
                            <span class="account-balance <%= compte.getSolde().compareTo(java.math.BigDecimal.ZERO) >= 0 ? "balance-positive" : "balance-negative" %>">
                                <%= compte.getSolde() %> €
                            </span>
                        </p>
                        <p><strong>Découvert autorisé:</strong> <%= compte.getDecouvertAutorise() %> €</p>
                    </div>
                </div>
            </div>


            <!-- Liste des mouvements -->
            <div class="card fade-in" style="margin-top: 2rem;">
                <div class="card-title">Historique des Mouvements</div>
                <% if (mouvements != null && !mouvements.isEmpty()) { %>
                    <div class="table-container">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Date</th>
                                    <th>Type</th>
                                    <th>Montant</th>
                                    <th>Statut</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (MouvementCourantDTO mouvement : mouvements) { 
                                    boolean estValide = false;
                                    String dernierStatut = "En attente";
                                    
                                    if (mouvement.getDernierStatut() != null) {
                                        dernierStatut = mouvement.getDernierStatut().getStatut().getLibelle();
                                        estValide = !"En attente".equals(dernierStatut);
                                    }
                                %>
                                    <tr>
                                        <td><%= mouvement.getId() %></td>
                                        <td><%= mouvement.getDateMouvement() != null ? mouvement.getDateMouvement().toString() : "N/A" %></td>
                                        <td>
                                            <span class="badge <%= mouvement.getTypeMouvement().getTypeSolde() > 0 ? "badge-success" : "badge-warning" %>">
                                                <%= mouvement.getTypeMouvement().getLibelle() %>
                                            </span>
                                        </td>
                                        <td style="color: <%= mouvement.getTypeMouvement().getTypeSolde() > 0 ? "green" : "red" %>">
                                            <strong><%= mouvement.getMontant() %> €</strong>
                                        </td>
                                        <td>
                                            <span class="badge <%= estValide ? "badge-success" : "badge-secondary" %>">
                                                <%= dernierStatut %>
                                            </span>
                                        </td>
                                        <td>
                                            <% if (!estValide) { %>
                                                <form action="${pageContext.request.contextPath}/compte-courant/mouvements" method="post" style="display: inline;">
                                                    <input type="hidden" name="action" value="valider">
                                                    <input type="hidden" name="mouvementId" value="<%= mouvement.getId() %>">
                                                    <input type="hidden" name="compteId" value="<%= compte.getId() %>">
                                                    <button type="submit" class="btn btn-primary btn-sm">Valider</button>
                                                </form>
                                                <form action="${pageContext.request.contextPath}/compte-courant/mouvements" method="post" style="display: inline;">
                                                    <input type="hidden" name="action" value="refuser">
                                                    <input type="hidden" name="mouvementId" value="<%= mouvement.getId() %>">
                                                    <input type="hidden" name="compteId" value="<%= compte.getId() %>">
                                                    <button type="submit" class="btn btn-outline btn-sm">Refuser</button>
                                                </form>
                                            <% } else { %>
                                                <span class="badge badge-success">Déjà <%= dernierStatut %></span>
                                            <% } %>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } else { %>
                    <div class="card" style="background-color: #fff3cd; color: #856404;">
                        Aucun mouvement trouvé pour ce compte.
                    </div>
                <% } %>
            </div>

        <% } else { %>
            <div class="card" style="background-color: #f8d7da; color: #721c24;">
                Compte non trouvé ou ID de compte manquant.
                <a href="${pageContext.request.contextPath}/compte-courant/comptes" class="btn btn-primary" style="margin-left: 10px;">
                    Retour à la liste
                </a>
            </div>
        <% } %>
    </div>
</body>
</html>
