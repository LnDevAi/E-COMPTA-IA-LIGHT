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
    public List<NoteAnnexe> genererNotesAnnexes(String systeme) {
        List<NoteAnnexe> notes = new ArrayList<>();
        if ("NORMAL".equalsIgnoreCase(systeme)) {
            // Notes pour OHADA NORMAL
            NoteAnnexe note1 = new NoteAnnexe();
            note1.setTitre("Méthodes comptables");
            note1.setContenu("Les états financiers sont établis conformément au SYSCOHADA révisé. Les principales méthodes comptables appliquées sont la valorisation des immobilisations, l’amortissement linéaire, la comptabilisation des provisions et la reconnaissance des produits et charges.");
            notes.add(note1);

            NoteAnnexe note2 = new NoteAnnexe();
            note2.setTitre("Immobilisations");
            note2.setContenu("Tableau des immobilisations corporelles, incorporelles et financières, avec détail des acquisitions, sorties et amortissements.");
            notes.add(note2);

            NoteAnnexe note3 = new NoteAnnexe();
            note3.setTitre("Provisions");
            note3.setContenu("Tableau des provisions pour risques et charges, avec nature, montant et évolution sur l’exercice.");
            notes.add(note3);

            NoteAnnexe note4 = new NoteAnnexe();
            note4.setTitre("Dettes");
            note4.setContenu("Détail des dettes financières, fournisseurs, fiscales et sociales, échéances et garanties éventuelles.");
            notes.add(note4);

            NoteAnnexe note5 = new NoteAnnexe();
            note5.setTitre("Créances");
            note5.setContenu("Détail des créances clients, fiscales, sociales et autres, avec analyse de l’ancienneté.");
            notes.add(note5);

            NoteAnnexe note6 = new NoteAnnexe();
            note6.setTitre("Engagements hors bilan");
            note6.setContenu("Liste des engagements donnés et reçus, cautions, garanties, contrats importants.");
            notes.add(note6);

            NoteAnnexe note7 = new NoteAnnexe();
            note7.setTitre("Informations diverses");
            note7.setContenu("Effectif, rémunérations, événements postérieurs à la clôture, litiges en cours, etc.");
            notes.add(note7);
        } else if ("MINIMAL".equalsIgnoreCase(systeme)) {
            // Notes pour OHADA MINIMAL
            NoteAnnexe note1 = new NoteAnnexe();
            note1.setTitre("Méthodes comptables (Minimal)");
            note1.setContenu("Les états financiers sont établis selon le Système Minimal de Trésorerie OHADA. Les méthodes sont simplifiées, axées sur la trésorerie et les flux réels.");
            notes.add(note1);

            NoteAnnexe note2 = new NoteAnnexe();
            note2.setTitre("Tableau de trésorerie");
            note2.setContenu("Présentation du tableau de trésorerie, avec détail des encaissements et décaissements.");
            notes.add(note2);

            NoteAnnexe note3 = new NoteAnnexe();
            note3.setTitre("Engagements hors bilan (Minimal)");
            note3.setContenu("Liste des engagements donnés et reçus, contrats importants, garanties.");
            notes.add(note3);

            NoteAnnexe note4 = new NoteAnnexe();
            note4.setTitre("Informations diverses (Minimal)");
            note4.setContenu("Effectif, événements postérieurs à la clôture, litiges en cours, etc.");
            notes.add(note4);
        } else {
            // Système inconnu ou non géré : notes par défaut
            NoteAnnexe note = new NoteAnnexe();
            note.setTitre("Note système inconnu");
            note.setContenu("Aucune note annexe spécifique disponible pour le système : " + systeme);
            notes.add(note);
        }
        return notes;
    }
}
