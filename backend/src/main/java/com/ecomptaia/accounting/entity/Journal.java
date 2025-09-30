package com.ecomptaia.accounting.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "journaux")
@Data
public class Journal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(unique = true, nullable = false, length = 10)
    private String code;
    
    @Column(nullable = false, length = 100)
    private String libelle;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeJournal type;
    
    @Column(nullable = false)
    private Boolean actif = true;
    
    public enum TypeJournal {
        ACHAT, VENTE, BANQUE, CAISSE, OPERATIONS_DIVERSES
    }
}
