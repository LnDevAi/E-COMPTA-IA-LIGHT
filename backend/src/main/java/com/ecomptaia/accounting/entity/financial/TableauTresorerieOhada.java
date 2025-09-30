package com.ecomptaia.accounting.entity.financial;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tableaux_tresorerie_ohada")
@Data
public class TableauTresorerieOhada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String exercice;

    @ElementCollection
    private List<String> lignesTresorerie; // À modéliser selon le rapport AUDCIF

    private double soldeInitial;
    private double soldeFinal;
}
