// MouvementService.java
package com.example.centralisateur.service;

import com.example.centralisateur.dto.MouvementCourantDTO;
import com.example.centralisateur.dto.TypeMouvementCourantDTO;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class MouvementService {

    public List<MouvementCourantDTO> getMouvementsByCompte(Long compteId) {
        Response response = UtilService.get(UtilService.getCourantAPI() + "/mouvements/compte/" + compteId);
        if (response.getStatus() == 200) {
            return response.readEntity(new jakarta.ws.rs.core.GenericType<List<MouvementCourantDTO>>() {
            });
        }
        return null;
    }

    public List<TypeMouvementCourantDTO> getAllTypesMouvement() {
        Response response = UtilService.get(UtilService.getCourantAPI() + "/mouvements/types");
        if (response.getStatus() == 200) {
            return response.readEntity(new jakarta.ws.rs.core.GenericType<List<TypeMouvementCourantDTO>>() {
            });
        }
        return null;
    }

    public Response creerMouvement(Long compteId, Long typeMouvementId, Double montant, Long utilisateurId) {
        // Formater le montant avec la locale anglaise pour avoir un point décimal
        String montantFormate = String.format(Locale.US, "%.2f", montant);

        String mouvementJson = String.format(
                "{\"compte\":{\"id\":%d},\"idClient\":1,\"montant\":%s}",
                compteId,
                montantFormate
        );

        Response response = UtilService.postJson(
                UtilService.getCourantAPI() + "/mouvements?typeMouvementId=" + typeMouvementId + "&utilisateurId="
                        + utilisateurId,
                mouvementJson);

        return response;
    }

    public boolean validerMouvement(Long mouvementId, Long utilisateurId, boolean valider) {
        Response response = UtilService.postJson(
                UtilService.getCourantAPI() + "/mouvements/" + mouvementId + "/valider?utilisateurId=" + utilisateurId
                        + "&reponse=" + valider,
                null);

        return response.getStatus() == 200;
    }
}