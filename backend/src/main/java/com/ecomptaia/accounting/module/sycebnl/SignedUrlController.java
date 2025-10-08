package com.ecomptaia.accounting.module.sycebnl;

import com.ecomptaia.accounting.security.SignedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sycebnl")
@PreAuthorize("hasRole('USER')")
public class SignedUrlController {
    @Autowired
    private SignedUrlService signedUrlService;

    @PostMapping("/pieces-justificatives/{id}/signed-url")
    public ResponseEntity<String> generate(@PathVariable Long id) {
        return ResponseEntity.ok(signedUrlService.sign(String.valueOf(id)));
    }
}
