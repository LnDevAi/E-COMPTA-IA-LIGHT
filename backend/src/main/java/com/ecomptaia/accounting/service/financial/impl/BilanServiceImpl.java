
package com.ecomptaia.accounting.service.financial.impl;

import com.ecomptaia.accounting.entity.financial.Bilan;
import com.ecomptaia.accounting.service.financial.BilanService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.entity.LigneEcriture;
import com.ecomptaia.accounting.entity.financial.LigneBilan;
import com.ecomptaia.accounting.repository.CompteComptableRepository;
import com.ecomptaia.accounting.repository.EcritureComptableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@Service
public class BilanServiceImpl implements BilanService {
    @Autowired
    private CompteComptableRepository compteComptableRepository;
    @Autowired
    private EcritureComptableRepository ecritureComptableRepository;

    @Override
    public Bilan genererBilan(LocalDate dateDebut, LocalDate dateFin) {
        List<CompteComptable> comptes = compteComptableRepository.findByActifTrue();
        List<EcritureComptable> ecritures = ecritureComptableRepository.findByDateEcritureBetween(dateDebut, dateFin);
        List<LigneBilan> actif = new ArrayList<>();
        List<LigneBilan> passif = new ArrayList<>();
        double totalActif = 0.0;
        double totalPassif = 0.0;

        for (CompteComptable compte : comptes) {
            double solde = 0.0;
            for (EcritureComptable ecriture : ecritures) {
                for (LigneEcriture ligne : ecriture.getLignes()) {
                    if (ligne.getCompte().getId().equals(compte.getId())) {
                        solde += ligne.getMontantDebit().doubleValue();
                        solde -= ligne.getMontantCredit().doubleValue();
                    }
                }
            }
            LigneBilan ligneBilan = new LigneBilan();
            ligneBilan.setIntitule(compte.getLibelle());
            ligneBilan.setMontant(solde);
            if (compte.getType() == CompteComptable.TypeCompte.ACTIF) {
                ligneBilan.setType("ACTIF");
                actif.add(ligneBilan);
                totalActif += solde;
            } else if (compte.getType() == CompteComptable.TypeCompte.PASSIF) {
                ligneBilan.setType("PASSIF");
                passif.add(ligneBilan);
                totalPassif += solde;
            }
        }

        Bilan bilan = new Bilan();
        bilan.setActif(actif);
        bilan.setPassif(passif);
        bilan.setTotalActif(totalActif);
        bilan.setTotalPassif(totalPassif);
        return bilan;
    }
}
