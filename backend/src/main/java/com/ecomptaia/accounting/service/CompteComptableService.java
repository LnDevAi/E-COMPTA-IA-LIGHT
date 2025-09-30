package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.repository.CompteComptableRepository;
import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.repository.EcritureComptableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompteComptableService {
    private final CompteComptableRepository repository;
    private final EcritureComptableRepository ecritureRepository;

    
    @Transactional
    public List<EcritureComptable> obtenirGrandLivre(String numero, java.time.LocalDate debut, java.time.LocalDate fin) {
        CompteComptable compte = repository.findByNumero(numero)
            .orElseThrow(() -> new RuntimeException("Compte " + numero + " introuvable"));
        return ecritureRepository.findByCompteAndDateEcritureBetween(compte, debut, fin);
    }
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

    public static class SoldeCompte {
        public String numero;
        public String libelle;
        public double debit;
        public double credit;
        public double solde;
        public SoldeCompte(String numero, String libelle, double debit, double credit) {
            this.numero = numero;
            this.libelle = libelle;
            this.debit = debit;
            this.credit = credit;
            this.solde = debit - credit;
        }
    }

    public java.util.List<SoldeCompte> calculerBalance(java.time.LocalDate debut, java.time.LocalDate fin) {
        java.util.List<CompteComptable> comptes = obtenirTousLesComptes();
        java.util.List<SoldeCompte> balance = new java.util.ArrayList<>();
        for (CompteComptable compte : comptes) {
            double debit = 0;
            double credit = 0;
            java.util.List<EcritureComptable> ecritures = ecritureRepository.findByCompteAndDateEcritureBetween(compte, debut, fin);
            for (EcritureComptable ecriture : ecritures) {
                for (var ligne : ecriture.getLignes()) {
                    if (ligne.getCompte().getNumero().equals(compte.getNumero())) {
                        debit += ligne.getMontantDebit() != null ? ligne.getMontantDebit().doubleValue() : 0;
                        credit += ligne.getMontantCredit() != null ? ligne.getMontantCredit().doubleValue() : 0;
                    }
                }
            }
            balance.add(new SoldeCompte(compte.getNumero(), compte.getLibelle(), debit, credit));
        }
        return balance;
    }
}
