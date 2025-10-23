// StatutMouvementDTO.java
package com.example.comptecourant.dto;

import java.time.LocalDateTime;
import com.example.comptecourant.model.StatutMouvement;

public class StatutMouvementDTO {
    private StatutDTO statut;
    private Long idUtilisateur;
    private LocalDateTime dateChangement;

    public StatutMouvementDTO(StatutMouvement sm) {
        statut = new StatutDTO(sm.getStatut());
        idUtilisateur = sm.getIdUtilisateur(); // Correction : initialiser cette propriété
        dateChangement = sm.getDateChangement(); // Correction : initialiser cette propriété
    }

    // Getters and Setters
    public StatutDTO getStatut() {
        return statut;
    }

    public void setStatut(StatutDTO statut) {
        this.statut = statut;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public LocalDateTime getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDateTime dateChangement) {
        this.dateChangement = dateChangement;
    }
}