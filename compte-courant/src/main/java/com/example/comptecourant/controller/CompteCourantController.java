// CompteCourantController.java
package com.example.comptecourant.controller;

import com.example.comptecourant.model.CompteCourant;
import com.example.comptecourant.service.CompteCourantService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/comptes")
@Produces(MediaType.APPLICATION_JSON)
public class CompteCourantController {

    @Inject
    private CompteCourantService service;

    @GET
    public List<CompteCourant> getAllComptes() {
        return service.getAllComptes();
    }

    @GET
    @Path("/{id}")
    public Response getCompteById(@PathParam("id") Long id) {
        CompteCourant compte = service.getCompteById(id);
        if (compte != null) {
            return Response.ok(compte).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCompte(CompteCourant compte) {
        try {
            CompteCourant created = service.createCompte(compte);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}