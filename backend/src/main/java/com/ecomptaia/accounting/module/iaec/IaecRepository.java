package com.ecomptaia.accounting.module.iaec;

import com.ecomptaia.accounting.entity.PieceComptableGed;
import com.ecomptaia.accounting.entity.EcritureComptable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IaecRepository extends JpaRepository<PieceComptableGed, Long> {
    // Recherche des pièces à analyser, etc.
    List<PieceComptableGed> findByEcritureIsNull(); // Pièces non encore associées à une écriture
}
