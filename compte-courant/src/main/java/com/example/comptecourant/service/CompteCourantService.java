// CompteCourantService.java
package com.example.comptecourant.service;

import com.example.comptecourant.model.CompteCourant;
import com.example.comptecourant.repository.CompteCourantRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class CompteCourantService {

    @Inject
    private CompteCourantRepository repository;

    public List<CompteCourant> getAllComptes() {
        return repository.findAll();
    }

    public CompteCourant getCompteById(Long id) {
        return repository.findById(id);
    }

    public CompteCourant createCompte(CompteCourant compte) {
        return repository.save(compte);
    }
}