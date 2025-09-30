package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.Journal;
import com.ecomptaia.accounting.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JournalService {
    
    private final JournalRepository repository;
    
    @Transactional
    public Journal creerJournal(Journal journal) {
        if (repository.findByCode(journal.getCode()).isPresent()) {
            throw new RuntimeException("Le journal " + journal.getCode() + " existe déjà");
        }
        return repository.save(journal);
    }
    
    public List<Journal> obtenirTousLesJournaux() {
        return repository.findByActifTrue();
    }
    
    public Journal obtenirJournalParCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Journal " + code + " introuvable"));
    }
    
    @Transactional
    public Journal modifierJournal(UUID id, Journal journalModifie) {
        Journal journal = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal introuvable"));
        
        journal.setLibelle(journalModifie.getLibelle());
        journal.setType(journalModifie.getType());
        journal.setActif(journalModifie.getActif());
        
        return repository.save(journal);
    }
}
