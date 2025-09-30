package com.ecomptaia.accounting.service.financial.impl;

import com.ecomptaia.accounting.entity.financial.CompteResultat;
import com.ecomptaia.accounting.service.financial.CompteResultatService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;

@Service
public class CompteResultatServiceImpl implements CompteResultatService {
    @org.springframework.beans.factory.annotation.Autowired
    private com.ecomptaia.accounting.repository.EcritureComptableRepository ecritureComptableRepository;
    @Override
    public CompteResultat genererCompteResultat(LocalDate dateDebut, LocalDate dateFin) {
            List<com.ecomptaia.accounting.entity.EcritureComptable> ecritures = ecritureComptableRepository.findByDateEcritureBetween(dateDebut, dateFin);
            List<com.ecomptaia.accounting.entity.financial.LigneResultat> produits = new ArrayList<>();
            List<com.ecomptaia.accounting.entity.financial.LigneResultat> charges = new ArrayList<>();
            double totalProduits = 0.0;
            double totalCharges = 0.0;

            for (com.ecomptaia.accounting.entity.EcritureComptable ecriture : ecritures) {
                for (com.ecomptaia.accounting.entity.LigneEcriture ligne : ecriture.getLignes()) {
                    com.ecomptaia.accounting.entity.CompteComptable.TypeCompte type = ligne.getCompte().getType();
                    String intitule = ligne.getCompte().getLibelle();
                    String numero = ligne.getCompte().getNumero();
                    double montantCredit = ligne.getMontantCredit().doubleValue();
                    double montantDebit = ligne.getMontantDebit().doubleValue();
                    if (type == com.ecomptaia.accounting.entity.CompteComptable.TypeCompte.PRODUIT) {
                        produits.add(new com.ecomptaia.accounting.entity.financial.LigneResultat(intitule, montantCredit, "PRODUIT"));
                        totalProduits += montantCredit;
                    } else if (type == com.ecomptaia.accounting.entity.CompteComptable.TypeCompte.CHARGE) {
                        charges.add(new com.ecomptaia.accounting.entity.financial.LigneResultat(intitule, montantDebit, "CHARGE"));
                        totalCharges += montantDebit;
                    }
                }
            }

            CompteResultat resultat = new CompteResultat();
            resultat.setProduits(produits);
            resultat.setCharges(charges);
            resultat.setTotalProduits(totalProduits);
            resultat.setTotalCharges(totalCharges);
            resultat.setResultatNet(totalProduits - totalCharges);
            return resultat;
    }
}
