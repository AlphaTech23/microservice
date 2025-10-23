// MouvementController.java
package com.example.centralisateur.controller;

import com.example.centralisateur.dto.*;
import com.example.centralisateur.service.CompteCourantService;
import com.example.centralisateur.service.MouvementService;
import com.example.centralisateur.service.UtilService;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.random.RandomGeneratorFactory;

import jakarta.ws.rs.core.Response;

@WebServlet("/compte-courant/mouvements")
public class MouvementController extends HttpServlet {

    @Inject
    private MouvementService mouvementService;

    @Inject
    private CompteCourantService compteService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String compteIdParam = request.getParameter("compteId");

        if ("nouveau".equals(action)) {
            // Afficher le formulaire de création de mouvement
            afficherFormulaireNouveauMouvement(request, response);
        } else if (compteIdParam != null && !compteIdParam.isEmpty()) {
            // Afficher les mouvements d'un compte spécifique
            afficherMouvementsCompte(request, response, Long.parseLong(compteIdParam));
        } else {
            // Rediriger vers la liste des comptes si aucun compte spécifié
            response.sendRedirect("/comptes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("creer".equals(action)) {
            // Création d'un nouveau mouvement
            creerNouveauMouvement(request, response);
        } else if ("valider".equals(action)) {
            // Validation d'un mouvement
            validerMouvement(request, response, true);
        } else if ("refuser".equals(action)) {
            // Validation d'un mouvement
            validerMouvement(request, response, false);
        } else {
            // Action par défaut - redirection
            response.sendRedirect("/mouvements");
        }
    }

    private void afficherFormulaireNouveauMouvement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer la liste des comptes pour le select
        List<CompteCourantDTO> comptes = compteService.getAllComptes();
        request.setAttribute("comptes", comptes);

        // Récupérer les types de mouvement
        List<TypeMouvementCourantDTO> typesMouvement = mouvementService.getAllTypesMouvement();
        request.setAttribute("typesMouvement", typesMouvement);

        request.getRequestDispatcher("/compte-courant/nouveau-mouvement.jsp").forward(request, response);
    }

    private void afficherMouvementsCompte(HttpServletRequest request, HttpServletResponse response, Long compteId)
            throws ServletException, IOException {

        // Récupérer les informations du compte
        CompteCourantDTO compte = compteService.getCompteById(compteId);
        request.setAttribute("compte", compte);

        // Récupérer les mouvements du compte
        List<MouvementCourantDTO> mouvements = mouvementService.getMouvementsByCompte(compteId);
        request.setAttribute("mouvements", mouvements);

        // Récupérer les types de mouvement pour référence
        List<TypeMouvementCourantDTO> typesMouvement = mouvementService.getAllTypesMouvement();
        request.setAttribute("typesMouvement", typesMouvement);

        request.getRequestDispatcher("/compte-courant/mouvements.jsp").forward(request, response);
    }

    private void creerNouveauMouvement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String compteId = request.getParameter("compteId");
        String typeMouvementId = request.getParameter("typeMouvement");
        String montantStr = request.getParameter("montant");
        Response reponse = UtilService.get("/api/utilisateur/session");
        UtilisateurDTO user = null;
        if (reponse.getStatus() == 200) {
            user = reponse.readEntity(UtilisateurDTO.class);
        } else {
            response.sendRedirect("/login");
        }
        Response action = UtilService
                .get(UtilService.getGlobalAPI() + "/action-roles/table/mouvement_courant/required-role?action=insert");
        Integer roleRequis = 0;
        if (reponse.getStatus() == 200) {
            roleRequis = reponse.readEntity(Integer.class);
            System.out.println("Rôle requis: " + roleRequis);
        } else {
            System.out.println("Erreur: " + reponse.getStatus());
            request.setAttribute("error", "Erreur lors de la création du mouvement " + action.readEntity(String.class));
            afficherFormulaireNouveauMouvement(request, response);
            return;
        }
        if (roleRequis > user.getRole()) {
            request.setAttribute("error",
                    "Erreur lors de la création du mouvement. Vous ne possedez pas les privileges pour cette action");
            afficherFormulaireNouveauMouvement(request, response);
            return;
        }
        if (compteId != null && typeMouvementId != null && montantStr != null && !montantStr.isEmpty()) {
            try {
                Double montant = Double.parseDouble(montantStr);
                Response success = mouvementService.creerMouvement(
                        Long.parseLong(compteId),
                        Long.parseLong(typeMouvementId),
                        montant,
                        user.getId());

                if (success.getStatus() == 201) {
                    request.getSession().setAttribute("success", "Mouvement créé avec succès");
                    response.sendRedirect("/mouvements?compteId=" + compteId);
                    return;
                } else {
                    request.setAttribute("error",
                            "Erreur lors de la création du mouvement " + success.readEntity(String.class));
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Format de montant invalide");
            }
        } else {
            request.setAttribute("error", "Tous les champs sont obligatoires");
        }

        // En cas d'erreur, réafficher le formulaire
        afficherFormulaireNouveauMouvement(request, response);
    }

    private void validerMouvement(HttpServletRequest request, HttpServletResponse response, boolean reponse)
            throws IOException {

        try {
            String mouvementId = request.getParameter("mouvementId");
            String compteId = request.getParameter("compteId");

            Response userApi = UtilService.get("/api/utilisateur/session");
            UtilisateurDTO user = null;
            if (userApi.getStatus() == 200) {
                user = userApi.readEntity(UtilisateurDTO.class);
            } else {
                response.sendRedirect("/login");
            }
            Response action = UtilService.get(
                    UtilService.getGlobalAPI() + "/action-roles/table/mouvement_courant/required-role?action=validate");
            Integer roleRequis = 0;
            if (action.getStatus() == 200) {
                roleRequis = action.readEntity(Integer.class);
                System.out.println("Rôle requis: " + roleRequis);
            } else {
                System.out.println("Erreur: " + action.getStatus());
                request.setAttribute("error",
                        "Erreur lors de la création du mouvement " + action.readEntity(String.class));
                afficherFormulaireNouveauMouvement(request, response);
                return;
            }
            if (roleRequis > user.getRole()) {
                request.setAttribute("error",
                        "Erreur lors de la création du mouvement. Vous ne possedez pas les privileges pour cette action");
                afficherFormulaireNouveauMouvement(request, response);
                return;
            }

            if (mouvementId != null && compteId != null) {
                try {
                    boolean success = mouvementService.validerMouvement(
                            Long.parseLong(mouvementId),
                            user.getId(), reponse // utilisateurId = 1 pour l'exemple
                    );

                    if (success) {
                        request.getSession().setAttribute("success", "Mouvement validé avec succès");
                    } else {
                        request.getSession().setAttribute("error", "Erreur lors de la validation du mouvement");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("error", "ID de mouvement invalide");
                }
            } else {
                request.getSession().setAttribute("error", "Paramètres manquants");
            }

            response.sendRedirect("/mouvements?compteId=" + compteId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}