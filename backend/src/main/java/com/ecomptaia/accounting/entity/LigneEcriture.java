package com.ecomptaia.accounting.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "lignes_ecriture")
@Data
public class LigneEcriture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "ecriture_id", nullable = false)
    private EcritureComptable ecriture;
    
    @ManyToOne
    @JoinColumn(name = "compte_id", nullable = false)
    private CompteComptable compte;
    
    @Column(length = 500)
    private String libelle;
    
    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal montantDebit = BigDecimal.ZERO;
    
    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal montantCredit = BigDecimal.ZERO;
    
    private Integer ordre;
}
