package com.ecomptaia.accounting.entity.financial;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
public class TestDataFinancialEntities {
    @Autowired
    private LigneResultatRepository ligneResultatRepository;
    @Autowired
    private LigneBilanRepository ligneBilanRepository;
    @Autowired
    private NoteAnnexeRepository noteAnnexeRepository;
    @Autowired
    private BilanRepository bilanRepository;
    @Autowired
    private CompteResultatRepository compteResultatRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void insertTestData() {
        LigneResultat produit = new LigneResultat("Ventes", 100000.0, "Produit");
        LigneResultat charge = new LigneResultat("Salaires", 40000.0, "Charge");
        ligneResultatRepository.save(produit);
        ligneResultatRepository.save(charge);

        LigneBilan actif = new LigneBilan();
        actif.setIntitule("Immobilisations");
        actif.setMontant(200000.0);
        actif.setType("Actif");
        ligneBilanRepository.save(actif);

        LigneBilan passif = new LigneBilan();
        passif.setIntitule("Capitaux propres");
        passif.setMontant(150000.0);
        passif.setType("Passif");
        ligneBilanRepository.save(passif);

        NoteAnnexe note = new NoteAnnexe();
        note.setTitre("Méthodes comptables");
        note.setContenu("Méthode d’amortissement linéaire");
        noteAnnexeRepository.save(note);

        Bilan bilan = new Bilan();
        bilan.setActif(Arrays.asList(actif));
        bilan.setPassif(Arrays.asList(passif));
        bilan.setTotalActif(200000.0);
        bilan.setTotalPassif(150000.0);
        bilanRepository.save(bilan);

        CompteResultat compteResultat = new CompteResultat();
        compteResultat.setProduits(Arrays.asList(produit));
        compteResultat.setCharges(Arrays.asList(charge));
        compteResultat.setTotalProduits(100000.0);
        compteResultat.setTotalCharges(40000.0);
        compteResultat.setResultatNet(60000.0);
        compteResultatRepository.save(compteResultat);
    }
}
