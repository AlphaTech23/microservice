package com.example.prets.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="pret")
public class Pret {
    @Id
    private Long id;
    private double montant;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
}
