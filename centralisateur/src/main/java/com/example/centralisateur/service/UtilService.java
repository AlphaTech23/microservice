package com.example.centralisateur.service;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class UtilService {

    private static final String GLOBAL_API_URL = "http://localhost:5000";
    private static final String USER_URL = "/utilisateur";
    private static final String COURANT_API_URL = "http://localhost:8180/compte-courant";
    private static final String DEPOT_API_URL = "http://localhost:5240/compte-depot";
    private static final String PRET_API_URL = "http://localhost:8280/prets";


    public static Response postForm(String url, Form data) {
        try (Client client = ClientBuilder.newClient()) {
            return client.target(url)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(data, MediaType.APPLICATION_FORM_URLENCODED));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Erreur POST Form: " + e.getMessage()).build();
        }
    }

    public static Response postJson(String url, Object data) {
        try (Client client = ClientBuilder.newClient()) {
            return client.target(url)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(data));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Erreur POST JSON: " + e.getMessage()).build();
        }
    }

    public static Response get(String url) {
        try (Client client = ClientBuilder.newClient()) {
            return client.target(url)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Erreur GET: " + e.getMessage()).build();
        }
    }

    public static String getGlobalAPI() {
        return GLOBAL_API_URL;
    }

    public static String getUserAPI() {
        return GLOBAL_API_URL + USER_URL;
    }

    public static String getCourantAPI() {
        return COURANT_API_URL;
    }

    public static String getDepotAPI() {
        return DEPOT_API_URL;
    }

    public static String getPretAPI() {
        return PRET_API_URL;
    }
}
