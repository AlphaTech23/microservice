// TypeMouvementRepository.java
package com.example.comptecourant.repository;

import com.example.comptecourant.model.TypeMouvementCourant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TypeMouvementRepository {

    @PersistenceContext(unitName = "comptecourantPU")
    private EntityManager em;

    public List<TypeMouvementCourant> findAll() {
        return em.createQuery("SELECT t FROM TypeMouvementCourant t", TypeMouvementCourant.class).getResultList();
    }

    public TypeMouvementCourant findById(Long id) {
        return em.find(TypeMouvementCourant.class, id);
    }
}