package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.CompteComptable;

import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.repository.EntrepriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntrepriseService {
    private final EntrepriseRepository entrepriseRepository;
    private final PlanComptableService planComptableService;

    // Service pour la génération des états financiers AUDCIF
    private final com.ecomptaia.accounting.service.financial.EtatFinancierOhadaService etatFinancierOhadaService;

    @Transactional
    public Entreprise creerEntreprise(Entreprise entreprise) {
        Entreprise saved = entrepriseRepository.save(entreprise);
        // Charger le plan comptable du système choisi
        String codeSysteme = entreprise.getSystemeComptable().getCode();
        if (codeSysteme != null) {
            // Ici, on pourrait charger le plan par défaut du système (exécution du script SQL ou import programmatique)
            // Exemple : planComptableService.importerPlanComptable(codeSysteme, comptesParDefaut);
            // À enrichir selon la logique métier et la source des comptes
        }
        return saved;
    }
        /**
         * Automatisation de l'import du plan comptable lors de la création d'entreprise.
         * Pour AUDCIF, le plan peut être unique et les états financiers diffèrent selon le type (NORMAL/MINIMAL).
         */
        @Transactional
        public Entreprise creerEntrepriseAutomatique(Entreprise entreprise) {
            Entreprise saved = entrepriseRepository.save(entreprise);
            String codeSysteme = entreprise.getSystemeComptable().getCode();
            if (codeSysteme != null) {
                // Import automatique du plan comptable
                List<CompteComptable> comptesDefaut = planComptableService.getPlanComptableBySysteme(codeSysteme);
                if (comptesDefaut != null && !comptesDefaut.isEmpty()) {
                    planComptableService.importerPlanComptable(codeSysteme, comptesDefaut);
                }
                // Pour AUDCIF, gérer le type de système (NORMAL/MINIMAL)
                if ("AUDCIF".equalsIgnoreCase(codeSysteme) && entreprise.getTypeSystemeAudcif() != null) {
                        // Génération automatique des états financiers AUDCIF selon le type
                        etatFinancierOhadaService.genererEtatsPourEntreprise(saved, "2025");
                }
            }
            return saved;
        }

    public List<Entreprise> obtenirToutesEntreprises() {
        return entrepriseRepository.findAll();
    }

    public Entreprise obtenirEntrepriseParId(UUID id) {
        return entrepriseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));
    }
}
