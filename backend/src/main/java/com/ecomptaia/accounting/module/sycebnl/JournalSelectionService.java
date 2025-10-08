package com.ecomptaia.accounting.module.sycebnl;

import com.ecomptaia.accounting.entity.Journal;
import com.ecomptaia.accounting.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class JournalSelectionService {
    @Autowired
    private JournalRepository journalRepository;

    public Optional<Journal> select(String libellePJ, String typePJ, String ocrText) {
        // Simple rules: ventes->VE, achats->AC, banque->BQ, caisse->CA else OD
        String text = (ocrText + " " + libellePJ + " " + typePJ).toLowerCase();
        if (text.contains("vente")) return journalRepository.findByCode("VE");
        if (text.contains("achat") || text.contains("facture")) return journalRepository.findByCode("AC");
        if (text.contains("banque")) return journalRepository.findByCode("BQ");
        if (text.contains("caisse")) return journalRepository.findByCode("CA");
        return journalRepository.findByCode("OD");
    }
}
