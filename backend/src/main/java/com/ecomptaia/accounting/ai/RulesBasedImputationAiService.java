package com.ecomptaia.accounting.ai;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RulesBasedImputationAiService implements ImputationAiService {
    @Override
    public List<ProposedLine> proposeLines(String ocrText) {
        // Simple rules: detect keywords and output example lines
        List<ProposedLine> lines = new ArrayList<>();
        String lower = ocrText.toLowerCase();
        if (lower.contains("facture") || lower.contains("achat")) {
            ProposedLine l1 = new ProposedLine();
            l1.compteNumero = "6000"; l1.libelle = "Achats"; l1.debit = 50000; l1.credit = 0;
            lines.add(l1);
            ProposedLine l2 = new ProposedLine();
            l2.compteNumero = "401"; l2.libelle = "Fournisseurs"; l2.debit = 0; l2.credit = 50000;
            lines.add(l2);
        }
        return lines;
    }
}
