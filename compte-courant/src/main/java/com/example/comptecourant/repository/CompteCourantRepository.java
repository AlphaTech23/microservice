// CompteCourantRepository.java
package com.example.comptecourant.repository;

import java.util.List;

import com.example.comptecourant.model.CompteCourant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class CompteCourantRepository {

    @PersistenceContext(unitName = "comptecourantPU")
    private EntityManager em;

    public List<CompteCourant> findAll() {
        return em.createQuery("SELECT c FROM CompteCourant c", CompteCourant.class).getResultList();
    }

    public CompteCourant findById(Long id) {
        return em.find(CompteCourant.class, id);
    }

    public CompteCourant save(CompteCourant compte) {
        if (compte.getId() == null) {
            em.persist(compte);
            return compte;
        } else {
            return em.merge(compte);
        }
    }
}