package com.ecomptaia.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of(
            "message", "E-COMPTA-IA-LIGHT fonctionne !",
            "version", "2.0.0",
            "status", "OK"
        );
    }
}
