package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.service.CompteComptableService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class BalanceController {

    private final CompteComptableService compteComptableService;

    public BalanceController(CompteComptableService compteComptableService) {
        this.compteComptableService = compteComptableService;
    }

    @GetMapping("/balance")
    public ResponseEntity<List<CompteComptableService.SoldeCompte>> getBalance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<CompteComptableService.SoldeCompte> balance = compteComptableService.calculerBalance(debut, fin);
        return ResponseEntity.ok(balance);
    }
}
