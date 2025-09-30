package com.ecomptaia.accounting.repository;

import com.ecomptaia.accounting.entity.EcritureComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.ecomptaia.accounting.entity.CompteComptable;

@Repository
public interface EcritureComptableRepository extends JpaRepository<EcritureComptable, UUID> {
    Optional<EcritureComptable> findByNumero(String numero);
    List<EcritureComptable> findByDateEcritureBetween(LocalDate debut, LocalDate fin);
    List<EcritureComptable> findByStatut(EcritureComptable.StatutEcriture statut);
    List<EcritureComptable> findByJournalId(UUID journalId);
    List<EcritureComptable> findByCompteAndDateEcritureBetween(CompteComptable compte, LocalDate debut, LocalDate fin);
}
