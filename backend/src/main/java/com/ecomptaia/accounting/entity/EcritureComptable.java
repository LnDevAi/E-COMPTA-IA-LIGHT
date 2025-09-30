package com.ecomptaia.accounting.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ecritures_comptables")
@Data
public class EcritureComptable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(unique = true, length = 20)
    private String numero;
    
    @Column(nullable = false)
    private LocalDate dateEcriture;
    
    @Column(nullable = false, length = 500)
    private String libelle;
    
    @Column(length = 50)
    private String reference;
    
    @ManyToOne
    @JoinColumn(name = "journal_id")
    private Journal journal;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private CompteComptable compte;
    
    @OneToMany(mappedBy = "ecriture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneEcriture> lignes = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEcriture statut = StatutEcriture.BROUILLON;
    
    private LocalDateTime dateValidation;
    
    @Column(length = 100)
    private String validePar;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(length = 100)
    private String createdBy;
    
    public enum StatutEcriture {
        BROUILLON, VALIDEE, CLOTUREE
    }
    
    public boolean isEquilibree() {
        BigDecimal totalDebit = lignes.stream()
                .map(LigneEcriture::getMontantDebit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalCredit = lignes.stream()
                .map(LigneEcriture::getMontantCredit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return totalDebit.compareTo(totalCredit) == 0;
    }
}
