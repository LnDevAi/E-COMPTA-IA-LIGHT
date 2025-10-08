package com.ecomptaia.accounting.entity.financial;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tableaux_tresorerie_audcif")
@Data
public class TableauTresorerieAudcif {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String exercice;

    @ElementCollection
    private List<String> lignesTresorerie;

    private double soldeInitial;
    private double soldeFinal;
}
