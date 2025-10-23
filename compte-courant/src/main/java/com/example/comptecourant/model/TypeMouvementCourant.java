// TypeMouvementCourant.java
package com.example.comptecourant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_mouvement_courant")
public class TypeMouvementCourant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    @Column(name = "type_solde", nullable = false)
    private Integer typeSolde; // -1 pour débit, 1 pour crédit

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Integer getTypeSolde() { return typeSolde; }
    public void setTypeSolde(Integer typeSolde) { this.typeSolde = typeSolde; }
}