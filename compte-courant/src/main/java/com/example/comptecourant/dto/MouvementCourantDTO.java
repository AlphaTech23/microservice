package com.example.comptecourant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.comptecourant.model.MouvementCourant;

public class MouvementCourantDTO {

    private Long id;
    private Long idClient;
    private CompteCourantDTO compte;
    private TypeMouvementCourantDTO typeMouvement;
    private LocalDateTime dateMouvement;
    private BigDecimal montant;

    private List<StatutMouvementDTO> statuts = new ArrayList<>();

    public MouvementCourantDTO(MouvementCourant mvt) {
        id = mvt.getId();
        idClient = mvt.getIdClient();
        compte = new CompteCourantDTO(mvt.getCompte());
        typeMouvement = new TypeMouvementCourantDTO(mvt.getTypeMouvement());
        dateMouvement = mvt.getDateMouvement();
        montant = mvt.getMontant();

        if (mvt.getStatuts() != null) {
            for (var sm : mvt.getStatuts()) {
                statuts.add(new StatutMouvementDTO(sm));
            }
            statuts.sort((a, b) -> b.getDateChangement().compareTo(a.getDateChangement()));
        }
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (statuts != null && !statuts.isEmpty()) {
            return statuts.get(0);
        }
        return null;
    }

    public CompteCourantDTO getCompte() {
        return compte;
    }

    public void setCompte(CompteCourantDTO compte) {
        this.compte = compte;
    }

    public TypeMouvementCourantDTO getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvementCourantDTO typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

}
