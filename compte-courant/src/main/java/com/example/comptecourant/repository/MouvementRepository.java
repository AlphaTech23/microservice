// MouvementRepository.java
package com.example.comptecourant.repository;

import java.util.List;
import com.example.comptecourant.model.MouvementCourant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MouvementRepository {

    @PersistenceContext(unitName = "comptecourantPU")
    private EntityManager em;

    public List<MouvementCourant> findByCompteId(Long compteId) {
        return em.createQuery(
            "SELECT m FROM MouvementCourant m WHERE m.compte.id = :compteId ORDER BY m.dateMouvement DESC", 
            MouvementCourant.class)
            .setParameter("compteId", compteId)
            .getResultList();
    }

    public MouvementCourant findById(Long id) {
        // Correction : Utiliser find() au lieu de createQuery pour une recherche par ID
        return em.find(MouvementCourant.class, id);
    }

    public MouvementCourant save(MouvementCourant mouvement) {
        if (mouvement.getId() == null) {
            em.persist(mouvement);
            return mouvement;
        } else {
            return em.merge(mouvement);
        }
    }

    public List<MouvementCourant> findAll() {
        return em.createQuery("SELECT m FROM MouvementCourant m ORDER BY m.dateMouvement DESC", MouvementCourant.class)
                .getResultList();
    }
}