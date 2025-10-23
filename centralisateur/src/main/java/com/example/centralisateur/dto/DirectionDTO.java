package com.example.centralisateur.dto;


public class DirectionDTO {
    private Long id;

    private String libelle;
    private int niveau;

    // GETTERS / SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public int getNiveau() { return niveau; }
    public void setNiveau(int niveau) { this.niveau = niveau; }

    @Override
    public String toString() {
        return "{id=" + id + ", login='" + libelle + "', role=" + niveau + "}";
    }

}
