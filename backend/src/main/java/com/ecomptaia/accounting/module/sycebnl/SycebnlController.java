package com.ecomptaia.accounting.module.sycebnl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.ecomptaia.accounting.module.sycebnl.SycebnlOrganizationDto;
import com.ecomptaia.accounting.module.sycebnl.ValidationDto;

@RestController
@RequestMapping("/api/sycebnl")
@PreAuthorize("hasRole('USER')")
public class SycebnlController {
    @Autowired
    private SycebnlService sycebnlService;

    // CRUD Organisation SYCEBNL
    @PostMapping("/organizations")
    public ResponseEntity<?> createOrganization(@RequestBody SycebnlOrganizationDto dto) {
        return ResponseEntity.ok(sycebnlService.createOrganization(dto));
    }

    @GetMapping("/organizations/{id}")
    public ResponseEntity<?> getOrganization(@PathVariable Long id) {
        return ResponseEntity.ok(sycebnlService.getOrganization(id));
    }

    @GetMapping("/organizations")
    public ResponseEntity<List<SycebnlOrganizationDto>> listOrganizations() {
        return ResponseEntity.ok(sycebnlService.listOrganizations());
    }

    // Pièce justificative GED + IA
    @PostMapping("/pieces-justificatives/upload")
    public ResponseEntity<?> uploadPieceJustificative(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("libellePJ") String libellePJ,
                                                      @RequestParam("datePiece") String datePiece,
                                                      @RequestParam("typePJ") String typePJ,
                                                      @RequestParam("entrepriseId") Long entrepriseId,
                                                      @RequestParam("utilisateurId") Long utilisateurId) {
        return ResponseEntity.ok(sycebnlService.uploadPieceJustificative(file, libellePJ, datePiece, typePJ, entrepriseId, utilisateurId));
    }

    @PostMapping("/pieces-justificatives/{id}/analyse-ocr")
    public ResponseEntity<?> analyseOCR(@PathVariable Long id) {
        return ResponseEntity.ok(sycebnlService.analyseOCR(id));
    }

    @PostMapping("/pieces-justificatives/{id}/analyse-ia")
    public ResponseEntity<?> analyseIA(@PathVariable Long id) {
        return ResponseEntity.ok(sycebnlService.analyseIA(id));
    }

    @PostMapping("/pieces-justificatives/{id}/generer-propositions")
    public ResponseEntity<?> genererPropositions(@PathVariable Long id) {
        return ResponseEntity.ok(sycebnlService.genererPropositions(id));
    }

    @PostMapping("/pieces-justificatives/propositions/{id}/valider")
    public ResponseEntity<?> validerProposition(@PathVariable Long id, @RequestBody ValidationDto validation) {
        return ResponseEntity.ok(sycebnlService.validerProposition(id, validation));
    }

    @PostMapping("/pieces-justificatives/propositions/{id}/generer-ecriture")
    public ResponseEntity<?> genererEcriture(@PathVariable Long id) {
        return ResponseEntity.ok(sycebnlService.genererEcriture(id));
    }

    // États financiers SN/SMT, notes annexes
    @GetMapping("/etats-financiers")
    public ResponseEntity<?> getEtatsFinanciers(@RequestParam Long organisationId) {
        return ResponseEntity.ok(sycebnlService.getEtatsFinanciers(organisationId));
    }

    @PostMapping("/etats-financiers/{id}/notes-annexes")
    public ResponseEntity<?> genererNotesAnnexes(@PathVariable Long id) {
        return ResponseEntity.ok(sycebnlService.genererNotesAnnexes(id));
    }
}
