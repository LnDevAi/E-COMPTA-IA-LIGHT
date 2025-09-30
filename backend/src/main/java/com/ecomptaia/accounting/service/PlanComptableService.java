package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.SystemeComptable;
import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.repository.CompteComptableRepository;
import com.ecomptaia.accounting.repository.SystemeComptableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanComptableService {
    private final CompteComptableRepository compteComptableRepository;
    private final SystemeComptableRepository systemeComptableRepository;

    public List<CompteComptable> getPlanComptableBySysteme(String codeSysteme) {
           SystemeComptable systeme = systemeComptableRepository.findByCode(codeSysteme);
           return compteComptableRepository.findAll().stream()
                 .filter(c -> c.getSystemeComptable() != null && c.getSystemeComptable().getCode().equals(codeSysteme))
                 .toList();
    }

    @Transactional
    public void importerPlanComptable(String codeSysteme, List<CompteComptable> comptes) {
           SystemeComptable systeme = systemeComptableRepository.findByCode(codeSysteme);
           for (CompteComptable compte : comptes) {
              compte.setSystemeComptable(systeme);
              compteComptableRepository.save(compte);
           }
    }

       public SystemeComptable getSystemeComptableByCode(String codeSysteme) {
              return systemeComptableRepository.findByCode(codeSysteme);
       }
}
