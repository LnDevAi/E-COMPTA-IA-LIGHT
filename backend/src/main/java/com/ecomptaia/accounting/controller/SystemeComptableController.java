package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.SystemeComptable;
import com.ecomptaia.accounting.service.SystemeComptableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/systemes-comptables")
@RequiredArgsConstructor
public class SystemeComptableController {
    private final SystemeComptableService systemeComptableService;

    @PostMapping
    public SystemeComptable creerSysteme(@RequestBody SystemeComptable systeme) {
        return systemeComptableService.creerSysteme(systeme);
    }

    @GetMapping
    public List<SystemeComptable> getAll() {
        return systemeComptableService.obtenirTousSystemes();
    }

    @GetMapping("/{code}")
    public SystemeComptable getByCode(@PathVariable String code) {
        return systemeComptableService.obtenirParCode(code);
    }
}
