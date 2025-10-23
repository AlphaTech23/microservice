// CompteCourantDTO.java
package com.example.centralisateur.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CompteCourantDTO {
    private Long id;
    private ClientDTO client;
    private String numeroCompte;
    private BigDecimal solde;
    private LocalDate dateOuverture;
    private BigDecimal decouvertAutorise;

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