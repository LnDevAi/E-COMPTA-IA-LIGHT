package com.ecomptaia.accounting.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "systemes_comptables")
@Data
public class SystemeComptable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // ex: OHADA, FR, IFRS, USGAAP

    @Column(nullable = false)
    private String libelle;

    @Column(length = 1000)
    private String description;
}
