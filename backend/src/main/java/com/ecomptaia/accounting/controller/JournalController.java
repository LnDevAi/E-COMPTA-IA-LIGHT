package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.Journal;
import com.ecomptaia.accounting.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/journaux")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping
    public List<Journal> getAll() {
        return journalService.obtenirTousLesJournaux();
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Journal> getByCode(@PathVariable String code) {
        try {
            return ResponseEntity.ok(journalService.obtenirJournalParCode(code));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Journal> create(@RequestBody Journal journal) {
        try {
            return ResponseEntity.ok(journalService.creerJournal(journal));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journal> update(@PathVariable UUID id, @RequestBody Journal journal) {
        try {
            return ResponseEntity.ok(journalService.modifierJournal(id, journal));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Suppression ou désactivation à ajouter si besoin dans le service
}
