package com.ecomptaia.accounting.module.sycebnl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;

@Entity
@Table(name = "sycebnl_piece_justificative")
public class PieceJustificativeSycebnl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libellePJ;
    private LocalDate datePiece;
    private String typePJ;
    private Long entrepriseId;
    private Long utilisateurId;
    private String filePath;
    private String contentType;
    private long size;
    private String sha256;
    private String ocrResult;
    private String iaResult;
    private String status;
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibellePJ() { return libellePJ; }
    public void setLibellePJ(String libellePJ) { this.libellePJ = libellePJ; }
    public java.time.LocalDate getDatePiece() { return datePiece; }
    public void setDatePiece(java.time.LocalDate datePiece) { this.datePiece = datePiece; }
    public String getTypePJ() { return typePJ; }
    public void setTypePJ(String typePJ) { this.typePJ = typePJ; }
    public Long getEntrepriseId() { return entrepriseId; }
    public void setEntrepriseId(Long entrepriseId) { this.entrepriseId = entrepriseId; }
    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getOcrResult() { return ocrResult; }
    public void setOcrResult(String ocrResult) { this.ocrResult = ocrResult; }
    public String getIaResult() { return iaResult; }
    public void setIaResult(String iaResult) { this.iaResult = iaResult; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
    public String getSha256() { return sha256; }
    public void setSha256(String sha256) { this.sha256 = sha256; }
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
}
