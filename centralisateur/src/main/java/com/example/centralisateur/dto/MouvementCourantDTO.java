// MouvementCourantDTO.java
package com.example.centralisateur.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MouvementCourantDTO {
    private Long id;
    private TypeMouvementCourantDTO typeMouvement;
    private CompteCourantDTO compte;
    private Long idClient;
    private LocalDateTime dateMouvement;
    private BigDecimal montant;
    private List<StatutMouvementDTO> statuts;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeMouvementCourantDTO getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvementCourantDTO typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public CompteCourantDTO getCompte() {
        return compte;
    }

    public void setCompte(CompteCourantDTO compte) {
        this.compte = compte;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public List<StatutMouvementDTO> getStatuts() {
        return statuts;
    }

    public void setStatuts(List<StatutMouvementDTO> statuts) {
        this.statuts = statuts;
    }

    public StatutMouvementDTO getDernierStatut() {
        if (statuts == null || statuts.isEmpty()) {
            return null;
        }
        return statuts.stream()
                      .max((s1, s2) -> s1.getDateChangement().compareTo(s2.getDateChangement()))
                      .orElse(null);
    }
}