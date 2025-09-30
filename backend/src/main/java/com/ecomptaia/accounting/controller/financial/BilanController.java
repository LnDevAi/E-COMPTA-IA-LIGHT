package com.ecomptaia.accounting.controller.financial;

import com.ecomptaia.accounting.entity.financial.Bilan;
import com.ecomptaia.accounting.service.financial.BilanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/financial/bilan")
public class BilanController {
    @Autowired
    private BilanService bilanService;

    @GetMapping
    public Bilan getBilan(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return bilanService.genererBilan(dateDebut, dateFin);
    }
}
