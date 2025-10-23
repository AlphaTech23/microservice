// StatutMouvement.java
package com.example.comptecourant.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "statut_mouvement")
@IdClass(StatutMouvementId.class)
public class StatutMouvement {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_mouvement", nullable = false)
    private MouvementCourant mouvement;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_statut", nullable = false)
    private Statut statut;

    @Id
    @Column(name = "id_utilisateur", nullable = false)
    private Long idUtilisateur;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement = LocalDateTime.now();

    // Getters and Setters
    public MouvementCourant getMouvement() {
        return mouvement;
    }

    public void setMouvement(MouvementCourant mouvement) {
        this.mouvement = mouvement;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
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