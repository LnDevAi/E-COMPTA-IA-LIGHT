package com.ecomptaia.accounting.entity.financial;

import com.ecomptaia.accounting.entity.TypeSystemeAudcif;
import com.ecomptaia.accounting.entity.Entreprise;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "etats_financiers_audcif")
@Data
public class EtatFinancierAudcif {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeSystemeAudcif typeSystemeAudcif;

    @Column(nullable = false)
    private String exercice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bilan_id")
    private Bilan bilan;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_resultat_id")
    private CompteResultat compteResultat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_annexe_id")
    private NoteAnnexe noteAnnexe;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tableau_tresorerie_id")
    private TableauTresorerieAudcif tableauTresorerie;
}
