package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.entity.SystemeComptable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ValidationPlanComptableService {
    public boolean validerPlan(SystemeComptable systeme, List<CompteComptable> comptes) {
        // Exemple de validation : numérotation, type, nature, etc. à enrichir selon les règles métier
        for (CompteComptable compte : comptes) {
            if (compte.getNumero() == null || compte.getNumero().isEmpty()) return false;
            if (compte.getType() == null || compte.getNature() == null) return false;
            // Règles spécifiques par système (exemple)
            if (systeme.getCode().equals("AUDCIF") && !compte.getNumero().matches("[1-9][0-9]{2,}")) return false;
            if (systeme.getCode().equals("FR") && !compte.getNumero().matches("[1-9][0-9]{2,}")) return false;
            // ... autres règles
        }
        return true;
    }
}
