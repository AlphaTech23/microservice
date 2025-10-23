package com.example.centralisateur.service;

import com.example.centralisateur.dto.UtilisateurDTO;
import com.example.centralisateur.ejb.SessionUtilisateurEJB;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;

@Stateless
public class UtilisateurService {

    @EJB
    private SessionUtilisateurEJB sessionEJB;

    public Response authentifier(String login, String password) {
        Form form = new Form()
                .param("login", login)
                .param("password", password);
        return UtilService.postForm(UtilService.getUserAPI() + "/login", form);
    }

    public Response addSession(UtilisateurDTO utilisateur) {
        if (utilisateur == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Utilisateur invalide").build();
        }
        sessionEJB.creerSession(utilisateur);
        return Response.ok("Session créée").build();
    }

    public Response getUtilisateurActif() {
        if (sessionEJB.isConnecte()) {
            return Response.ok(sessionEJB.getUtilisateur()).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Aucune session active").build();
    }

    public Response logout() {
        sessionEJB.logout();
        return Response.ok("Déconnecté").build();
    }
}
