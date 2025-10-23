package com.example.comptecourant.service;

import com.example.comptecourant.model.*;
import com.example.comptecourant.dto.MouvementCourantDTO;
import com.example.comptecourant.dto.StatutMouvementDTO;
import com.example.comptecourant.repository.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class MouvementService {

    @Inject
    private MouvementRepository mouvementRepository;

    @Inject
    private CompteCourantRepository compteRepository;

    @Inject
    private TypeMouvementRepository typeMouvementRepository;

    @Inject
    private StatutRepository statutRepository;

    @Inject
    private StatutMouvementRepository statutMouvementRepository;

    // --- Méthode utilitaire de conversion entité -> DTO ---
    private MouvementCourantDTO toDTO(MouvementCourant mvt) {
        return new MouvementCourantDTO(mvt);
    }

    // --- Retourne la liste des mouvements DTO pour un compte ---
    public List<MouvementCourantDTO> getMouvementsByCompte(Long compteId) {
        List<MouvementCourant> mouvements = mouvementRepository.findByCompteId(compteId);
        return mouvements.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Retourne tous les mouvements en DTO ---
    public List<MouvementCourantDTO> getAllMouvements() {
        return mouvementRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Création d'un mouvement et retour DTO ---
    public MouvementCourantDTO createMouvement(MouvementCourant mouvement, Long typeMouvementId, Long utilisateurId) {
        TypeMouvementCourant typeMouvement = typeMouvementRepository.findById(typeMouvementId);
        if (typeMouvement == null)
            throw new RuntimeException("Type de mouvement non trouvé");

        CompteCourant compte = compteRepository.findById(mouvement.getCompte().getId());
        if (compte == null)
            throw new RuntimeException("Compte non trouvé");

        mouvement.setTypeMouvement(typeMouvement);
        mouvement.setCompte(compte);

        MouvementCourant savedMouvement = mouvementRepository.save(mouvement);

        // Statut initial
        Statut statutInitial = statutRepository.findById(1L); // "En attente"
        if (statutInitial != null) {
            StatutMouvement sm = new StatutMouvement();
            sm.setMouvement(savedMouvement);
            sm.setStatut(statutInitial);
            sm.setIdUtilisateur(utilisateurId);
            statutMouvementRepository.save(sm);
        }

        return toDTO(savedMouvement);
    }

    // --- Validation d'un mouvement et retour DTO mis à jour ---
    public MouvementCourantDTO validerMouvement(Long mouvementId, Long utilisateurId, boolean reponse) {
        MouvementCourant mouvement = mouvementRepository.findById(mouvementId);
        if (mouvement == null) {
            throw new RuntimeException("Mouvement non trouvé avec l'ID: " + mouvementId);
        }

        // Vérifier si le mouvement n'est pas déjà validé
        StatutMouvementDTO dernierStatut = toDTO(mouvement).getDernierStatut();
        if (dernierStatut != null && "Validé".equals(dernierStatut.getStatut().getLibelle())) {
            throw new RuntimeException("Le mouvement est déjà validé");
        }

        Long id = reponse ? 2L : 3L;

        // Statut "Validé"
        Statut statutValide = statutRepository.findById(id);
        if (statutValide != null) {
            StatutMouvement sm = new StatutMouvement();
            sm.setMouvement(mouvement);
            sm.setStatut(statutValide);
            sm.setIdUtilisateur(utilisateurId);
            statutMouvementRepository.save(sm);
        }

        // Mise à jour du solde du compte
        CompteCourant compte = mouvement.getCompte();
        BigDecimal nouveauSolde = compte.getSolde().add(
                mouvement.getMontant().multiply(BigDecimal.valueOf(mouvement.getTypeMouvement().getTypeSolde())));

        // Vérification du découvert autorisé
        if (nouveauSolde.compareTo(compte.getDecouvertAutorise().negate()) < 0) {
            throw new RuntimeException("Solde insuffisant: le découvert autorisé est dépassé");
        }

        compte.setSolde(nouveauSolde);
        compteRepository.save(compte);

        return toDTO(mouvement);
    }

    public List<TypeMouvementCourant> getAllTypesMouvement() {
        return typeMouvementRepository.findAll();
    }
}
