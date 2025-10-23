// StatutDTO.java
package com.example.comptecourant.dto;

import com.example.comptecourant.model.Statut;

public class StatutDTO {
    private Long id;
    private String libelle;

    public StatutDTO(Statut s) {
        id = s.getId();
        libelle = s.getLibelle();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}