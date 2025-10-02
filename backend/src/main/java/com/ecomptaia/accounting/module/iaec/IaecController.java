
package com.ecomptaia.accounting.module.iaec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/iaec")
public class IaecController {
    @Autowired
    private IaecService iaecService;

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzePiece(@RequestBody Long pieceId) {
        var pieceOpt = iaecService.getPiece(pieceId);
        if (pieceOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pièce non trouvée");
        }
        var ecriture = iaecService.proposeEcriture(pieceOpt.get());
        return ResponseEntity.ok(ecriture);
    }
}
