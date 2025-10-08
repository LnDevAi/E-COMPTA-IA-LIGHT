
package com.ecomptaia.accounting.config;

import com.ecomptaia.accounting.entity.SystemeComptable;
import com.ecomptaia.accounting.entity.TypeSystemeAudcif;
import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.entity.Journal;
import com.ecomptaia.accounting.service.CompteComptableService;
import com.ecomptaia.accounting.service.JournalService;
import com.ecomptaia.accounting.service.SystemeComptableService;
import com.ecomptaia.accounting.service.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer {
    @Autowired
    private SystemeComptableService systemeComptableService;
    @Autowired
    private EntrepriseService entrepriseService;
    @Autowired
    private CompteComptableService compteComptableService;
    @Autowired
    private JournalService journalService;

    @PostConstruct
    public void initData() {
        if (systemeComptableService.obtenirTousSystemes().isEmpty()) {
            SystemeComptable audcif = new SystemeComptable();
            audcif.setCode("AUDCIF");
            audcif.setLibelle("AUDCIF");
            audcif.setDescription("Système comptable AUDCIF");
            audcif = systemeComptableService.creerSysteme(audcif);

            Entreprise entreprise = new Entreprise();
            entreprise.setNom("Entreprise Demo");
            entreprise.setPays("Cameroun");
            entreprise.setSystemeComptable(audcif);
            entreprise.setTypeSystemeAudcif(TypeSystemeAudcif.NORMAL);
            entreprise = entrepriseService.creerEntreprise(entreprise);

            // Initialisation du plan comptable AUDCIF (classes 1 à 7)
            int compteCount = 0;
            for (int classe = 1; classe <= 7; classe++) {
                for (int i = 0; i < 10; i++) {
                    CompteComptable compte = new CompteComptable();
                    compte.setNumero(classe + "00" + i);
                    compte.setLibelle("Compte classe " + classe + " n°" + i);
                    compte.setType(classe <= 2 ? CompteComptable.TypeCompte.ACTIF : (classe <= 4 ? CompteComptable.TypeCompte.CHARGE : CompteComptable.TypeCompte.PASSIF));
                    compte.setNature(classe <= 4 ? CompteComptable.NatureCompte.DEBIT : CompteComptable.NatureCompte.CREDIT);
                    compte.setSystemeComptable(audcif);
                    compteComptableService.creerCompte(compte);
                    compteCount++;
                }
            }

            // Création des journaux standards
            List<Journal.TypeJournal> types = Arrays.asList(
                Journal.TypeJournal.ACHAT,
                Journal.TypeJournal.VENTE,
                Journal.TypeJournal.BANQUE,
                Journal.TypeJournal.CAISSE,
                Journal.TypeJournal.OPERATIONS_DIVERSES
            );
            List<String> codes = Arrays.asList("AC", "VE", "BQ", "CA", "OD");
            List<String> libelles = Arrays.asList("Achats", "Ventes", "Banque", "Caisse", "Opérations diverses");
            for (int i = 0; i < codes.size(); i++) {
                Journal journal = new Journal();
                journal.setCode(codes.get(i));
                journal.setLibelle(libelles.get(i));
                journal.setType(types.get(i));
                journalService.creerJournal(journal);
            }

            System.out.println("Plan comptable AUDCIF initialisé avec " + compteCount + " comptes");
        }
    }
}
