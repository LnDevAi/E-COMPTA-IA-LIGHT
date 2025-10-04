package com.ecomptaia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Bienvenue sur l’API E-COMPTA-IA-LIGHT. Pour accéder aux fonctionnalités, utilisez les endpoints /api/...";
    }
}
