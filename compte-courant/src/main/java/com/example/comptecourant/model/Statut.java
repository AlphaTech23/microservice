// Statut.java
package com.example.comptecourant.model;

import java.util.*;
import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient;


@Entity
@Table(name = "statut")
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String libelle;
    
    @OneToMany(mappedBy = "statut", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonbTransient
    private List<StatutMouvement> mouvements = new ArrayList<>();

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

    public List<StatutMouvement> getMouvements() {
        return mouvements;
    }

    public void setMouvements(List<StatutMouvement> mouvements) {
        this.mouvements = mouvements;
    }

    // Méthode utilitaire pour ajouter un mouvement
    public void addMouvement(StatutMouvement statutMouvement) {
        mouvements.add(statutMouvement);
        statutMouvement.setStatut(this);
    }

    // Méthode utilitaire pour retirer un mouvement
    public void removeMouvement(StatutMouvement statutMouvement) {
        mouvements.remove(statutMouvement);
        statutMouvement.setStatut(null);
    }
}