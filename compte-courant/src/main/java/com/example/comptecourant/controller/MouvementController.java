package com.example.comptecourant.controller;

import com.example.comptecourant.dto.MouvementCourantDTO;
import com.example.comptecourant.model.MouvementCourant;
import com.example.comptecourant.model.TypeMouvementCourant;
import com.example.comptecourant.service.MouvementService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/mouvements")
@Produces(MediaType.APPLICATION_JSON)
public class MouvementController {

    @Inject
    private MouvementService service;

    @GET
    public List<MouvementCourantDTO> getAllMouvements() {
        return service.getAllMouvements();
    }

    @GET
    @Path("/compte/{compteId}")
    public List<MouvementCourantDTO> getMouvementsByCompte(@PathParam("compteId") Long compteId) {
        return service.getMouvementsByCompte(compteId);
    }

    @GET
    @Path("/types")
    public List<TypeMouvementCourant> getTypesMouvement() {
        return service.getAllTypesMouvement();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMouvement(
            @QueryParam("typeMouvementId") Long typeMouvementId,
            @QueryParam("utilisateurId") Long utilisateurId,
            MouvementCourant mouvement) {
        try {
            MouvementCourantDTO created = service.createMouvement(mouvement, typeMouvementId, utilisateurId);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{mouvementId}/valider")
    public Response validerMouvement(
            @PathParam("mouvementId") Long mouvementId,
            @QueryParam("reponse") boolean reponse,
            @QueryParam("utilisateurId") Long utilisateurId) {
        try {
            service.validerMouvement(mouvementId, utilisateurId, reponse);
            return Response.ok("Mouvement "+ (reponse ? "validé" : "rejeté") +" avec succès").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}