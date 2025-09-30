package com.ecomptaia.accounting.controller.financial;

import com.ecomptaia.accounting.entity.financial.CompteResultat;
import com.ecomptaia.accounting.service.financial.CompteResultatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/financial/compte-resultat")
public class CompteResultatController {
    @Autowired
    private CompteResultatService compteResultatService;

    @GetMapping
    public CompteResultat getCompteResultat(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return compteResultatService.genererCompteResultat(dateDebut, dateFin);
    }
}
