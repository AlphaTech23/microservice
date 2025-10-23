// CompteCourant.java
package com.example.comptecourant.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "compte_courant")
public class CompteCourant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @Column(name = "numero_compte", unique = true, nullable = false)
    private String numeroCompte;

    @Column(precision = 18, scale = 2)
    private BigDecimal solde = BigDecimal.ZERO;

    @Column(name = "date_ouverture")
    private LocalDate dateOuverture = LocalDate.now();

    @Column(name = "decouvert_autorise", precision = 18, scale = 2)
    private BigDecimal decouvertAutorise = BigDecimal.ZERO;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}