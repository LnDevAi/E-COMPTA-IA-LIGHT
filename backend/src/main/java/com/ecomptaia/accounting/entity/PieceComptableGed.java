
package com.ecomptaia.accounting.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PieceComptableGed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String originalFilename;
    private String contentType;
    private Long size;
    private LocalDateTime uploadDate;

    @Lob
    private byte[] data;

    // Optionnel : lien vers une écriture comptable
    @ManyToOne
    @JoinColumn(name = "ecriture_id")
    private EcritureComptable ecriture;

    // Optionnel : utilisateur ayant uploadé
    private String uploadedBy;
}
