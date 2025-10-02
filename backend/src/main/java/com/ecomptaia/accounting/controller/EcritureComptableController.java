package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.service.EcritureComptableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/ecritures")
public class EcritureComptableController {
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportEcrituresPdf() {
        List<EcritureComptable> ecritures = ecritureComptableService.obtenirEcrituresParPeriode(LocalDate.of(1900, 1, 1), LocalDate.now());
        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new com.itextpdf.text.Paragraph("Liste des écritures comptables"));
            com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(4);
            table.addCell("Date");
            table.addCell("Libellé");
            table.addCell("Journal");
            table.addCell("Montant");
            for (EcritureComptable e : ecritures) {
                double montant = e.getLignes() != null ? e.getLignes().stream().mapToDouble(l -> {
                    double debit = l.getMontantDebit() != null ? l.getMontantDebit().doubleValue() : 0;
                    double credit = l.getMontantCredit() != null ? l.getMontantCredit().doubleValue() : 0;
                    return debit - credit;
                }).sum() : 0;
                table.addCell(e.getDateEcriture() != null ? e.getDateEcriture().toString() : "");
                table.addCell(e.getLibelle());
                table.addCell(e.getJournal() != null ? e.getJournal().getCode() : "");
                table.addCell(String.format("%.2f", montant));
            }
            document.add(table);
            document.close();
            byte[] pdfBytes = baos.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ecritures.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    private final EcritureComptableService ecritureComptableService;

    public EcritureComptableController(EcritureComptableService ecritureComptableService) {
        this.ecritureComptableService = ecritureComptableService;
    }


    @GetMapping("/export/csv")
    public ResponseEntity<String> exportEcrituresCsv() {
        List<EcritureComptable> ecritures = ecritureComptableService.obtenirEcrituresParPeriode(LocalDate.of(1900, 1, 1), LocalDate.now());
        StringBuilder sb = new StringBuilder();
        sb.append("Date,Libellé,Journal,Montant\n");
        for (EcritureComptable e : ecritures) {
            double montant = e.getLignes() != null ? e.getLignes().stream().mapToDouble(l -> {
                double debit = l.getMontantDebit() != null ? l.getMontantDebit().doubleValue() : 0;
                double credit = l.getMontantCredit() != null ? l.getMontantCredit().doubleValue() : 0;
                return debit - credit;
            }).sum() : 0;
            sb.append(String.format("%s,%s,%s,%.2f\n", e.getDateEcriture(), e.getLibelle(), e.getJournal() != null ? e.getJournal().getCode() : "", montant));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ecritures.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(sb.toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EcritureComptable> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(ecritureComptableService.obtenirEcritureParId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EcritureComptable> create(@RequestBody EcritureComptable ecriture) {
        try {
            return ResponseEntity.ok(ecritureComptableService.creerEcriture(ecriture));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            ecritureComptableService.supprimerEcriture(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/valider")
    public ResponseEntity<EcritureComptable> valider(@PathVariable UUID id, @RequestParam String validePar) {
        try {
            return ResponseEntity.ok(ecritureComptableService.validerEcriture(id, validePar));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/periode")
    public List<EcritureComptable> getByPeriode(@RequestParam LocalDate debut, @RequestParam LocalDate fin) {
        return ecritureComptableService.obtenirEcrituresParPeriode(debut, fin);
    }

    @GetMapping("/journal/{journalId}")
    public List<EcritureComptable> getByJournal(@PathVariable UUID journalId) {
        return ecritureComptableService.obtenirEcrituresParJournal(journalId);
    }

    @GetMapping("/statut/{statut}")
    public List<EcritureComptable> getByStatut(@PathVariable EcritureComptable.StatutEcriture statut) {
        return ecritureComptableService.obtenirEcrituresParStatut(statut);
    }
}
