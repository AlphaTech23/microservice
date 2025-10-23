// MouvementCourant.java
package com.example.comptecourant.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import jakarta.json.bind.annotation.JsonbTransient;

@Entity
@Table(name = "mouvement_courant")
public class MouvementCourant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_type_mouvement", nullable = false)
    private TypeMouvementCourant typeMouvement;

    @ManyToOne
    @JoinColumn(name = "id_compte", nullable = false)
    private CompteCourant compte;

    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Column(name = "date_mouvement", nullable = false)
    private LocalDateTime dateMouvement = LocalDateTime.now();

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;

    @OneToMany(mappedBy = "mouvement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonbTransient
    private List<StatutMouvement> statuts = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeMouvementCourant getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvementCourant typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public CompteCourant getCompte() {
        return compte;
    }

    public void setCompte(CompteCourant compte) {
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

    public List<StatutMouvement> getStatuts() {
        return statuts;
    }

    public void setStatuts(List<StatutMouvement> statuts) {
        this.statuts = statuts;
    }

    // Méthode utilitaire pour ajouter un statut
    public void addStatut(StatutMouvement statutMouvement) {
        statuts.add(statutMouvement);
        statutMouvement.setMouvement(this);
    }

    // Méthode utilitaire pour retirer un statut
    public void removeStatut(StatutMouvement statutMouvement) {
        statuts.remove(statutMouvement);
        statutMouvement.setMouvement(null);
    }
}