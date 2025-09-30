package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.service.CompteComptableService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class GrandLivreController {

    private final CompteComptableService compteComptableService;

    public GrandLivreController(CompteComptableService compteComptableService) {
        this.compteComptableService = compteComptableService;
    }

    @GetMapping("/{numero}/grand-livre")
    public ResponseEntity<List<EcritureComptable>> getGrandLivre(
            @PathVariable String numero,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        try {
            List<EcritureComptable> ecritures = compteComptableService.obtenirGrandLivre(numero, debut, fin);
            return ResponseEntity.ok(ecritures);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
