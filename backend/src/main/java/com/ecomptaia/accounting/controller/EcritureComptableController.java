package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.service.EcritureComptableService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/ecritures")
public class EcritureComptableController {

    private final EcritureComptableService ecritureComptableService;

    public EcritureComptableController(EcritureComptableService ecritureComptableService) {
        this.ecritureComptableService = ecritureComptableService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EcritureComptable> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(ecritureComptableService.obtenirEcritureParId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EcritureComptable> create(@RequestBody EcritureComptable ecriture) {
        try {
            return ResponseEntity.ok(ecritureComptableService.creerEcriture(ecriture));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            ecritureComptableService.supprimerEcriture(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/valider")
    public ResponseEntity<EcritureComptable> valider(@PathVariable UUID id, @RequestParam String validePar) {
        try {
            return ResponseEntity.ok(ecritureComptableService.validerEcriture(id, validePar));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/periode")
    public List<EcritureComptable> getByPeriode(@RequestParam LocalDate debut, @RequestParam LocalDate fin) {
        return ecritureComptableService.obtenirEcrituresParPeriode(debut, fin);
    }

    @GetMapping("/journal/{journalId}")
    public List<EcritureComptable> getByJournal(@PathVariable UUID journalId) {
        return ecritureComptableService.obtenirEcrituresParJournal(journalId);
    }

    @GetMapping("/statut/{statut}")
    public List<EcritureComptable> getByStatut(@PathVariable EcritureComptable.StatutEcriture statut) {
        return ecritureComptableService.obtenirEcrituresParStatut(statut);
    }
}
