package com.ecomptaia.accounting.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "entreprises")
@Data
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String pays;

    @ManyToOne
    @JoinColumn(name = "systeme_comptable_id", nullable = false)
    private SystemeComptable systemeComptable;

    /**
     * Pour l'espace AUDCIF, permet de distinguer le Système Normal et le Système Minimal de Trésorerie.
     * null si non AUDCIF.
     */
    @Column(name = "type_systeme_audcif")
    @Enumerated(EnumType.STRING)
    private TypeSystemeAudcif typeSystemeAudcif;
}
