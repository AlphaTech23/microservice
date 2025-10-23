// StatutMouvementDTO.java
package com.example.centralisateur.dto;

import java.time.LocalDateTime;

public class StatutMouvementDTO {
    private StatutDTO statut;
    private Long idUtilisateur;
    private LocalDateTime dateChangement;

    // Getters and Setters
    public StatutDTO getStatut() { return statut; }
    public void setStatut(StatutDTO statut) { this.statut = statut; }
    public Long getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(Long idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    public LocalDateTime getDateChangement() { return dateChangement; }
    public void setDateChangement(LocalDateTime dateChangement) { this.dateChangement = dateChangement; }
}