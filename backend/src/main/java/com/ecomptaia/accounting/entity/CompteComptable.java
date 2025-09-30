package com.ecomptaia.accounting.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "comptes_comptables")
@Data
public class CompteComptable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(unique = true, nullable = false, length = 10)
    private String numero;
    
    @Column(nullable = false, length = 200)
    private String libelle;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCompte type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NatureCompte nature;
    
    @Column(length = 10)
    private String numeroParent;
    
    private Integer niveau;
    
    @Column(nullable = false)
    private Boolean actif = true;
    
        @ManyToOne
        @JoinColumn(name = "systeme_comptable_id", nullable = false)
        private SystemeComptable systemeComptable;
    
    public enum TypeCompte {
        ACTIF, PASSIF, CHARGE, PRODUIT
    }
    
    public enum NatureCompte {
        DEBIT, CREDIT
    }
}
