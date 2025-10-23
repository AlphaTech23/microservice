// StatutRepository.java
package com.example.comptecourant.repository;

import com.example.comptecourant.model.Statut;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StatutRepository {

    @PersistenceContext(unitName = "comptecourantPU")
    private EntityManager em;

    public List<Statut> findAll() {
        return em.createQuery("SELECT s FROM Statut s", Statut.class).getResultList();
    }

    public Statut findById(Long id) {
        return em.find(Statut.class, id);
    }
}