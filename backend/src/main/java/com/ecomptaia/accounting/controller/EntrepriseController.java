package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.service.EntrepriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/entreprises")
@RequiredArgsConstructor
public class EntrepriseController {
    private final EntrepriseService entrepriseService;

    @PostMapping
    public Entreprise creerEntreprise(@RequestBody Entreprise entreprise) {
        return entrepriseService.creerEntreprise(entreprise);
    }

        /**
         * Création automatisée d'entreprise avec import du plan comptable et gestion du type OHADA.
         */
        @PostMapping("/automatique")
        public Entreprise creerEntrepriseAutomatique(@RequestBody Entreprise entreprise) {
            return entrepriseService.creerEntrepriseAutomatique(entreprise);
        }

    @GetMapping
    public List<Entreprise> getAll() {
        return entrepriseService.obtenirToutesEntreprises();
    }

    @GetMapping("/{id}")
    public Entreprise getById(@PathVariable UUID id) {
        return entrepriseService.obtenirEntrepriseParId(id);
    }
}
