package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.service.CompteComptableService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comptes")
public class CompteComptableController {

    private final CompteComptableService compteComptableService;

    public CompteComptableController(CompteComptableService compteComptableService) {
        this.compteComptableService = compteComptableService;
    }

    @GetMapping
    public List<CompteComptable> getAll() {
        return compteComptableService.obtenirTousLesComptes();
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<CompteComptable> getByNumero(@PathVariable String numero) {
        try {
            return ResponseEntity.ok(compteComptableService.obtenirCompteParNumero(numero));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/type/{type}")
    public List<CompteComptable> getByType(@PathVariable CompteComptable.TypeCompte type) {
        return compteComptableService.obtenirComptesParType(type);
    }

    @PostMapping
    public ResponseEntity<CompteComptable> create(@RequestBody CompteComptable compte) {
        try {
            return ResponseEntity.ok(compteComptableService.creerCompte(compte));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompteComptable> update(@PathVariable UUID id, @RequestBody CompteComptable compte) {
        try {
            return ResponseEntity.ok(compteComptableService.modifierCompte(id, compte));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        try {
            compteComptableService.desactiverCompte(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
