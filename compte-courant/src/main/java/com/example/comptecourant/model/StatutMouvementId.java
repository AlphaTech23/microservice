// StatutMouvementId.java
package com.example.comptecourant.model;

import java.io.Serializable;
import java.util.Objects;

public class StatutMouvementId implements Serializable {
    private Long mouvement;
    private Long statut;
    private Long idUtilisateur;

    // Constructors, getters, equals, hashCode
    public StatutMouvementId() {}

    public StatutMouvementId(Long mouvement, Long statut, Long idUtilisateur) {
        this.mouvement = mouvement;
        this.statut = statut;
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatutMouvementId that = (StatutMouvementId) o;
        return Objects.equals(mouvement, that.mouvement) &&
               Objects.equals(statut, that.statut) &&
               Objects.equals(idUtilisateur, that.idUtilisateur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mouvement, statut, idUtilisateur);
    }
}