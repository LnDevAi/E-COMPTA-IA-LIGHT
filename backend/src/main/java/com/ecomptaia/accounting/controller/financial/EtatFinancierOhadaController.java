package com.ecomptaia.accounting.controller.financial;

import com.ecomptaia.accounting.entity.financial.EtatFinancierOhada;
import com.ecomptaia.accounting.service.financial.EtatFinancierOhadaService;
import com.ecomptaia.accounting.entity.Entreprise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/etats-financiers-ohada")
@RequiredArgsConstructor
public class EtatFinancierOhadaController {
    private final EtatFinancierOhadaService etatFinancierOhadaService;

    @PostMapping("/generer")
    public EtatFinancierOhada genererEtats(@RequestBody Entreprise entreprise, @RequestParam String exercice) {
        return etatFinancierOhadaService.genererEtatsPourEntreprise(entreprise, exercice);
    }
}
