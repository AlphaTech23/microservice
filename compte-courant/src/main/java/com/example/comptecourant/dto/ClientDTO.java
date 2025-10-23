package com.example.comptecourant.dto;

import com.example.comptecourant.model.Client;

public class ClientDTO {

    private Long id;
    private String nom;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.nom = client.getNom();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
