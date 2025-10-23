package com.example.comptecourant.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.comptecourant.model.CompteCourant;

public class CompteCourantDTO {
    private Long id;
    private ClientDTO client;
    private String numeroCompte;
    private BigDecimal solde;
    private LocalDate dateOuverture;
    private BigDecimal decouvertAutorise;

    public CompteCourantDTO(CompteCourant cc) {
        this.dateOuverture = cc.getDateOuverture();
        this.decouvertAutorise = cc.getDecouvertAutorise();
        this.id = cc.getId();
        this.client = new ClientDTO(cc.getClient());
        this.numeroCompte = cc.getNumeroCompte();
        this.solde = cc.getSolde();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public BigDecimal getDecouvertAutorise() {
        return decouvertAutorise;
    }

    public void setDecouvertAutorise(BigDecimal decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }
}