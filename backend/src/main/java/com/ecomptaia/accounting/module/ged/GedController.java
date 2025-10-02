
package com.ecomptaia.accounting.module.ged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/ged")
public class GedController {
    @Autowired
    private GedService gedService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPiece(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "uploadedBy", required = false) String uploadedBy) {
        try {
            var piece = gedService.saveFile(file, uploadedBy);
            return ResponseEntity.ok(piece);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload : " + e.getMessage());
        }
    }
}
