package com.ecomptaia.accounting.module.utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/inscription")
    public ResponseEntity<?> inscription(@RequestBody UtilisateurDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return ResponseEntity.ok(utilisateurService.inscription(dto));
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurDto>> listUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.listUtilisateurs());
    }

    @PostMapping("/abonnement")
    public ResponseEntity<?> abonnement(@RequestBody AbonnementDto dto) {
        return ResponseEntity.ok(utilisateurService.gererAbonnement(dto));
    }
}
