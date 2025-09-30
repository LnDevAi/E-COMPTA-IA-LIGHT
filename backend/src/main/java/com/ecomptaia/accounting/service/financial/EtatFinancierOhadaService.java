package com.ecomptaia.accounting.service.financial;

import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.entity.TypeSystemeOhada;
import com.ecomptaia.accounting.entity.financial.EtatFinancierOhada;
import com.ecomptaia.accounting.entity.financial.TypeEtatFinancierOhada;
import com.ecomptaia.accounting.entity.financial.Bilan;
import com.ecomptaia.accounting.entity.financial.CompteResultat;
import com.ecomptaia.accounting.entity.financial.NoteAnnexe;
import com.ecomptaia.accounting.entity.financial.TableauTresorerieOhada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtatFinancierOhadaService {
    @org.springframework.beans.factory.annotation.Autowired
    private BilanService bilanService;
    @org.springframework.beans.factory.annotation.Autowired
    private CompteResultatService compteResultatService;
    @org.springframework.beans.factory.annotation.Autowired
    private NoteAnnexeService noteAnnexeService;
    /**
     * Génère les états financiers adaptés selon le type OHADA (NORMAL ou MINIMAL).
     */
    public EtatFinancierOhada genererEtatsPourEntreprise(Entreprise entreprise, String exercice) {
        EtatFinancierOhada etat = new EtatFinancierOhada();
        etat.setEntreprise(entreprise);
        etat.setExercice(exercice);
        if (entreprise.getTypeSystemeOhada() == TypeSystemeOhada.NORMAL) {
            etat.setTypeSystemeOhada(TypeSystemeOhada.NORMAL);
                // Générer bilan et compte de résultat à partir des écritures
                java.time.LocalDate debut = java.time.LocalDate.of(Integer.parseInt(exercice), 1, 1);
                java.time.LocalDate fin = java.time.LocalDate.of(Integer.parseInt(exercice), 12, 31);
                Bilan bilan = bilanService.genererBilan(debut, fin);
                etat.setBilan(bilan);

                CompteResultat compteResultat = compteResultatService.genererCompteResultat(debut, fin);
                etat.setCompteResultat(compteResultat);

                    // Générer les notes annexes réglementaires
                    java.util.List<NoteAnnexe> notes = noteAnnexeService.genererNotesAnnexes();
                    if (!notes.isEmpty()) {
                        etat.setNoteAnnexe(notes.get(0)); // On peut enrichir pour stocker la liste complète
                    }
        } else if (entreprise.getTypeSystemeOhada() == TypeSystemeOhada.MINIMAL) {
            etat.setTypeSystemeOhada(TypeSystemeOhada.MINIMAL);
                // Générer tableau de trésorerie simplifié
                TableauTresorerieOhada tableau = new TableauTresorerieOhada();
                tableau.setExercice(exercice);
                tableau.setSoldeInitial(0);
                tableau.setSoldeFinal(0);
                etat.setTableauTresorerie(tableau);
                    // Notes annexes simplifiées
                    java.util.List<NoteAnnexe> notes = noteAnnexeService.genererNotesAnnexes();
                    if (!notes.isEmpty()) {
                        etat.setNoteAnnexe(notes.get(0));
                    }
        }
        // À enrichir selon la structure des états
        return etat;
    }
}
