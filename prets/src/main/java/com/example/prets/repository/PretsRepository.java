package com.example.prets.repository;

import com.example.prets.model.Pret;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class PretsRepository {

    @PersistenceContext(unitName="pretsPU")
    private EntityManager em;

    public List<Pret> findAll() {
        return em.createQuery("SELECT p FROM Pret p", Pret.class).getResultList();
    }
}
