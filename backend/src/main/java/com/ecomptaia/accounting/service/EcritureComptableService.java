package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.entity.LigneEcriture;
import com.ecomptaia.accounting.repository.EcritureComptableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EcritureComptableService {
    
    private final EcritureComptableRepository repository;
    
    @Transactional
    public EcritureComptable creerEcriture(EcritureComptable ecriture) {
        if (ecriture.getNumero() != null && repository.findByNumero(ecriture.getNumero()).isPresent()) {
            throw new RuntimeException("L'écriture " + ecriture.getNumero() + " existe déjà");
        }
        
        for (LigneEcriture ligne : ecriture.getLignes()) {
            ligne.setEcriture(ecriture);
        }
        
        return repository.save(ecriture);
    }
    
    @Transactional
    public EcritureComptable validerEcriture(UUID id, String validePar) {
        EcritureComptable ecriture = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Écriture introuvable"));
        
        if (!ecriture.isEquilibree()) {
            throw new RuntimeException("L'écriture n'est pas équilibrée (Débit != Crédit)");
        }
        
        ecriture.setStatut(EcritureComptable.StatutEcriture.VALIDEE);
        ecriture.setDateValidation(LocalDateTime.now());
        ecriture.setValidePar(validePar);
        
        return repository.save(ecriture);
    }
    
    public List<EcritureComptable> obtenirEcrituresParPeriode(LocalDate debut, LocalDate fin) {
        return repository.findByDateEcritureBetween(debut, fin);
    }
    
    public List<EcritureComptable> obtenirEcrituresParJournal(UUID journalId) {
        return repository.findByJournalId(journalId);
    }
    
    public List<EcritureComptable> obtenirEcrituresParStatut(EcritureComptable.StatutEcriture statut) {
        return repository.findByStatut(statut);
    }
    
    public EcritureComptable obtenirEcritureParId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Écriture introuvable"));
    }
    
    @Transactional
    public void supprimerEcriture(UUID id) {
        EcritureComptable ecriture = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Écriture introuvable"));
        
        if (ecriture.getStatut() == EcritureComptable.StatutEcriture.VALIDEE) {
            throw new RuntimeException("Impossible de supprimer une écriture validée");
        }
        
        repository.delete(ecriture);
    }
}
