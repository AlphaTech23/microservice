<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.centralisateur.dto.CompteCourantDTO" %>
<%
    List<CompteCourantDTO> comptes = (List<CompteCourantDTO>) request.getAttribute("comptes");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Comptes Courants</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>

    <!-- ===================== NAVBAR ===================== -->
    <nav class="navbar">
        <div class="navbar-brand">Banque Centralisateur</div>
        <div class="navbar-nav">
            <a href="${pageContext.request.contextPath}/compte-courant/comptes" class="nav-link active">Comptes Courants</a>
            <a href="${pageContext.request.contextPath}/compte-courant/mouvements?action=nouveau" class="nav-link">Nouveau Mouvement</a>
            <a href="${pageContext.request.contextPath}/login?logout=true" class="nav-link">Déconnexion</a>
        </div>
    </nav>

    <!-- ===================== CONTENU PRINCIPAL ===================== -->
    <main class="fade-in" style="padding: 2rem 3rem;">

        <!-- HEADER DE PAGE -->
        <div class="header" style="margin-bottom: 1.5rem;">
            <h1>Comptes Courants</h1>
            <p>Gestion des comptes courants de la banque</p>
        </div>

        <!-- MESSAGES DE SESSION -->
        <% if (session.getAttribute("success") != null) { %>
            <div class="alert alert-success">
                <%= session.getAttribute("success") %>
            </div>
            <% session.removeAttribute("success"); %>
        <% } %>

        <% if (session.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= session.getAttribute("error") %>
            </div>
            <% session.removeAttribute("error"); %>
        <% } %>

        <!-- LISTE DES COMPTES -->
        <div class="card" style="margin-bottom: 2rem;">
            <div class="card-title">Liste des Comptes Courants</div>
            <div class="card-body">

                <% if (comptes != null && !comptes.isEmpty()) { %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Numéro de Compte</th>
                                <th>Client ID</th>
                                <th>Solde</th>
                                <th>Date Ouverture</th>
                                <th>Découvert Autorisé</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (CompteCourantDTO compte : comptes) { %>
                                <tr>
                                    <td><%= compte.getId() %></td>
                                    <td><strong><%= compte.getNumeroCompte() %></strong></td>
                                    <td><%= compte.getIdClient() %></td>
                                    <td style="color: <%= compte.getSolde().compareTo(java.math.BigDecimal.ZERO) >= 0 ? "green" : "red" %>;">
                                        <strong><%= compte.getSolde() %> €</strong>
                                    </td>
                                    <td><%= compte.getDateOuverture() %></td>
                                    <td><%= compte.getDecouvertAutorise() %> €</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/compte-courant/mouvements?compteId=<%= compte.getId() %>"
                                           class="btn btn-outline">Voir Mouvements</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="alert alert-warning">
                        Aucun compte courant trouvé.
                    </div>
                <% } %>

            </div>
        </div>

        <!-- STATISTIQUES -->
        <div class="card">
            <div class="card-title">Statistiques</div>
            <div class="card-body">
                <div class="row" style="display: flex; gap: 1rem; justify-content: space-around;">
                    
                    <div class="col" style="flex: 1; text-align: center;">
                        <h3 style="color: #4b4acb; font-size: 2rem; margin-bottom: 0.3rem;">
                            <%= comptes != null ? comptes.size() : 0 %>
                        </h3>
                        <p style="color: #666;">Total des Comptes</p>
                    </div>

                    <div class="col" style="flex: 1; text-align: center;">
                        <h3 style="color: #28a745; font-size: 2rem; margin-bottom: 0.3rem;">
                            <% 
                                if (comptes != null) {
                                    long comptesPositifs = 0;
                                    for (CompteCourantDTO c : comptes) {
                                        if (c.getSolde().compareTo(java.math.BigDecimal.ZERO) > 0) {
                                            comptesPositifs++;
                                        }
                                    }
                                    out.print(comptesPositifs);
                                } else {
                                    out.print("0");
                                }
                            %>
                        </h3>
                        <p style="color: #666;">Comptes Créditeurs</p>
                    </div>

                </div>
            </div>
        </div>

    </main>

    <!-- ===================== FOOTER ===================== -->
    <footer class="footer">
        &copy; <%= java.time.Year.now() %> Banque Centralisateur - Tous droits réservés.
    </footer>

</body>
</html>
