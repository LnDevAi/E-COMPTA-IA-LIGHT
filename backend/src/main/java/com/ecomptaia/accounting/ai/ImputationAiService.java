package com.ecomptaia.accounting.ai;

import java.util.List;

public interface ImputationAiService {
    List<ProposedLine> proposeLines(String ocrText);

    class ProposedLine {
        public String compteNumero;
        public String libelle;
        public double debit;
        public double credit;
    }
}
