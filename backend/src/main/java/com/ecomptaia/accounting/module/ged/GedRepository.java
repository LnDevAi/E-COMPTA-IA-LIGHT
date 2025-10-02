package com.ecomptaia.accounting.module.ged;

import com.ecomptaia.accounting.entity.PieceComptableGed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GedRepository extends JpaRepository<PieceComptableGed, Long> {
    // Recherche personnalisée si besoin
}
