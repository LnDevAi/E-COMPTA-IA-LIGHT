package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.service.PlanComptableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.ecomptaia.accounting.service.ValidationPlanComptableService;
import org.springframework.http.ResponseEntity;

import com.ecomptaia.accounting.entity.SystemeComptable;

@RestController
@RequestMapping("/api/plan-comptable")
@RequiredArgsConstructor
public class PlanComptableController {
    private final PlanComptableService planComptableService;
    private final ValidationPlanComptableService validationPlanComptableService;

    @GetMapping("/{systeme}")
    public List<CompteComptable> getPlanComptable(@PathVariable String systeme) {
        return planComptableService.getPlanComptableBySysteme(systeme);
    }

    @PostMapping("/{systeme}/import")
        public ResponseEntity<?> importerPlan(@PathVariable String systeme, @RequestBody List<CompteComptable> comptes) {
            SystemeComptable sys = planComptableService.getSystemeComptableByCode(systeme);
            if (!validationPlanComptableService.validerPlan(sys, comptes)) {
                return ResponseEntity.badRequest().body("Plan comptable non conforme au système " + systeme);
            }
            planComptableService.importerPlanComptable(systeme, comptes);
            return ResponseEntity.ok().build();
    }
}
