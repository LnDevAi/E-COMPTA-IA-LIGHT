package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.repository.CompteComptableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompteComptableService {
    
    private final CompteComptableRepository repository;
    
    @Transactional
    public CompteComptable creerCompte(CompteComptable compte) {
        if (repository.findByNumero(compte.getNumero()).isPresent()) {
            throw new RuntimeException("Le compte " + compte.getNumero() + " existe déjà");
        }
        compte.setNiveau(compte.getNumero().length());
        return repository.save(compte);
    }
    
    public List<CompteComptable> obtenirTousLesComptes() {
        return repository.findByActifTrue();
    }
    
    public CompteComptable obtenirCompteParNumero(String numero) {
        return repository.findByNumero(numero)
                .orElseThrow(() -> new RuntimeException("Compte " + numero + " introuvable"));
    }
    
    public List<CompteComptable> obtenirComptesParType(CompteComptable.TypeCompte type) {
        return repository.findByType(type);
    }
    
    @Transactional
    public CompteComptable modifierCompte(UUID id, CompteComptable compteModifie) {
        CompteComptable compte = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));
        
        compte.setLibelle(compteModifie.getLibelle());
        compte.setType(compteModifie.getType());
        compte.setNature(compteModifie.getNature());
        compte.setActif(compteModifie.getActif());
        
        return repository.save(compte);
    }
    
    @Transactional
    public void desactiverCompte(UUID id) {
        CompteComptable compte = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));
        compte.setActif(false);
        repository.save(compte);
    }
}
