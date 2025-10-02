package com.ecomptaia.accounting.module.utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {
    private final List<UtilisateurDto> utilisateurs = new ArrayList<>();
    private final List<AbonnementDto> abonnements = new ArrayList<>();

    public UtilisateurDto inscription(UtilisateurDto dto) {
        utilisateurs.add(dto);
        return dto;
    }

    public List<UtilisateurDto> listUtilisateurs() {
        return utilisateurs;
    }

    public AbonnementDto gererAbonnement(AbonnementDto dto) {
        abonnements.add(dto);
        return dto;
    }
}
