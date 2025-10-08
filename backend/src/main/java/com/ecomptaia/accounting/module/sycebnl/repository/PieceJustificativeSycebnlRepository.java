package com.ecomptaia.accounting.module.sycebnl.repository;

import com.ecomptaia.accounting.module.sycebnl.entity.PieceJustificativeSycebnl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceJustificativeSycebnlRepository extends JpaRepository<PieceJustificativeSycebnl, Long> {
    boolean existsBySha256(String sha256);
}
