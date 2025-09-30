package com.ecomptaia.accounting.repository;

import com.ecomptaia.accounting.entity.CompteComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompteComptableRepository extends JpaRepository<CompteComptable, UUID> {
    Optional<CompteComptable> findByNumero(String numero);
    List<CompteComptable> findByActifTrue();
    List<CompteComptable> findByType(CompteComptable.TypeCompte type);
    List<CompteComptable> findByNumeroStartingWith(String prefix);
}
