
package com.ecomptaia.accounting.entity.financial;

import com.ecomptaia.accounting.entity.TypeSystemeOhada;

import com.ecomptaia.accounting.entity.Entreprise;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "etats_financiers_ohada")
@Data
public class EtatFinancierOhada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeSystemeOhada typeSystemeOhada;

    @Column(nullable = false)
    private String exercice;


    // Système Normal : Bilan, Compte de Résultat, Notes Annexes
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bilan_id")
    private Bilan bilan;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_resultat_id")
    private CompteResultat compteResultat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_annexe_id")
    private NoteAnnexe noteAnnexe;

    // Système Minimal : Tableau de trésorerie
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tableau_tresorerie_id")
    private TableauTresorerieOhada tableauTresorerie;
}
