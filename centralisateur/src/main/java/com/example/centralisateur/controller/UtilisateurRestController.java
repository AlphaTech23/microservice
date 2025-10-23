package com.example.centralisateur.controller;

import com.example.centralisateur.service.UtilisateurService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/utilisateur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurRestController {

    
    @Inject
    private UtilisateurService service;

    @GET
    @Path("/session")
    public Response getUtilisateurActif() {
        return service.getUtilisateurActif();
    }
}
