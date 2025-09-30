package com.ecomptaia.accounting.service.financial.impl;

import com.ecomptaia.accounting.entity.financial.NoteAnnexe;
import com.ecomptaia.accounting.service.financial.NoteAnnexeService;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

import java.util.ArrayList;

@Service
public class NoteAnnexeServiceImpl implements NoteAnnexeService {
    @Override
    public List<NoteAnnexe> genererNotesAnnexes() {
           List<NoteAnnexe> notes = new ArrayList<>();

           // 1. Méthodes comptables
           NoteAnnexe note1 = new NoteAnnexe();
           note1.setTitre("Méthodes comptables");
           note1.setContenu("Les états financiers sont établis conformément au SYSCOHADA révisé. Les principales méthodes comptables appliquées sont la valorisation des immobilisations, l’amortissement linéaire, la comptabilisation des provisions et la reconnaissance des produits et charges.");
           notes.add(note1);

           // 2. Immobilisations
           NoteAnnexe note2 = new NoteAnnexe();
           note2.setTitre("Immobilisations");
           note2.setContenu("Tableau des immobilisations corporelles, incorporelles et financières, avec détail des acquisitions, sorties et amortissements.");
           notes.add(note2);

           // 3. Provisions
           NoteAnnexe note3 = new NoteAnnexe();
           note3.setTitre("Provisions");
           note3.setContenu("Tableau des provisions pour risques et charges, avec nature, montant et évolution sur l’exercice.");
           notes.add(note3);

           // 4. Dettes
           NoteAnnexe note4 = new NoteAnnexe();
           note4.setTitre("Dettes");
           note4.setContenu("Détail des dettes financières, fournisseurs, fiscales et sociales, échéances et garanties éventuelles.");
           notes.add(note4);

           // 5. Créances
           NoteAnnexe note5 = new NoteAnnexe();
           note5.setTitre("Créances");
           note5.setContenu("Détail des créances clients, fiscales, sociales et autres, avec analyse de l’ancienneté.");
           notes.add(note5);

           // 6. Engagements hors bilan
           NoteAnnexe note6 = new NoteAnnexe();
           note6.setTitre("Engagements hors bilan");
           note6.setContenu("Liste des engagements donnés et reçus, cautions, garanties, contrats importants.");
           notes.add(note6);

           // 7. Informations diverses
           NoteAnnexe note7 = new NoteAnnexe();
           note7.setTitre("Informations diverses");
           note7.setContenu("Effectif, rémunérations, événements postérieurs à la clôture, litiges en cours, etc.");
           notes.add(note7);

           return notes;
    }
}
