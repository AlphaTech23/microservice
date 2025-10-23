// StatutMouvementRepository.java
package com.example.comptecourant.repository;

import com.example.comptecourant.model.StatutMouvement;
import com.example.comptecourant.model.StatutMouvementId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StatutMouvementRepository {

    @PersistenceContext(unitName = "comptecourantPU")
    private EntityManager em;

    public List<StatutMouvement> findByMouvementId(Long mouvementId) {
        return em.createQuery(
            "SELECT sm FROM StatutMouvement sm WHERE sm.mouvement.id = :mouvementId ORDER BY sm.dateChangement DESC", 
            StatutMouvement.class)
            .setParameter("mouvementId", mouvementId)
            .getResultList();
    }

    public StatutMouvement save(StatutMouvement statutMouvement) {
        em.persist(statutMouvement);
        return statutMouvement;
    }

    public StatutMouvement findById(StatutMouvementId id) {
        return em.find(StatutMouvement.class, id);
    }
}