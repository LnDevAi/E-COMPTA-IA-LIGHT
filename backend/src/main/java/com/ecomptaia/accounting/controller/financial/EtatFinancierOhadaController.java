package com.ecomptaia.accounting.controller.financial;

import com.ecomptaia.accounting.entity.financial.EtatFinancierAudcif;
import com.ecomptaia.accounting.service.financial.EtatFinancierOhadaService;
import com.ecomptaia.accounting.entity.Entreprise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/etats-financiers-audcif","/api/etats-financiers-ohada"})
@RequiredArgsConstructor
public class EtatFinancierAudcifController {
    private final EtatFinancierOhadaService etatFinancierOhadaService;

    @PostMapping("/generer")
    public EtatFinancierAudcif genererEtats(@RequestBody Entreprise entreprise, @RequestParam String exercice) {
        return (EtatFinancierAudcif) etatFinancierOhadaService.genererEtatsPourEntreprise(entreprise, exercice);
    }
}
