// TypeMouvementCourantDTO.java
package com.example.centralisateur.dto;

public class TypeMouvementCourantDTO {
    private Long id;
    private String libelle;
    private Integer typeSolde;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Integer getTypeSolde() { return typeSolde; }
    public void setTypeSolde(Integer typeSolde) { this.typeSolde = typeSolde; }
}