package com.example.centralisateur.ejb;

import com.example.centralisateur.dto.UtilisateurDTO;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Remove;
import jakarta.ejb.Stateful;

@Stateful
public class SessionUtilisateurEJB {

    private UtilisateurDTO utilisateur;
    private boolean connecte;

    @PostConstruct
    public void init() {
        this.connecte = false;
    }

    public void creerSession(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
        this.connecte = true;
        System.out.println("[SESSION] Connecté : " + utilisateur.getLogin());
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public boolean isConnecte() {
        return connecte;
    }

    @Remove
    public void logout() {
        System.out.println("[SESSION] Destruction du bean pour : "
                + (utilisateur != null ? utilisateur.getLogin() : "inconnu"));
        this.utilisateur = null;
        this.connecte = false;
    }
}
