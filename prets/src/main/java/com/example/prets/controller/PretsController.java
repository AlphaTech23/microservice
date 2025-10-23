package com.example.prets.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.prets.service.PretsServiceEJB;
import com.example.prets.model.Pret;
import jakarta.inject.Inject;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON) // ← TRÈS IMPORTANT
public class PretsController {

    @Inject
    private PretsServiceEJB service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pret> getAllSimple() {
        return service.getAllPrets();
    }
}