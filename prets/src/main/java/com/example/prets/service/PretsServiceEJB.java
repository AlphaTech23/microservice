package com.example.prets.service;

import com.example.prets.model.Pret;
import com.example.prets.repository.PretsRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class PretsServiceEJB {

    @Inject
    private PretsRepository repo;

    public List<Pret> getAllPrets() {
        return repo.findAll();
    }
}
