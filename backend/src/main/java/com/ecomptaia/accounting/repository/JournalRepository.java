package com.ecomptaia.accounting.repository;

import com.ecomptaia.accounting.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JournalRepository extends JpaRepository<Journal, UUID> {
    Optional<Journal> findByCode(String code);
    List<Journal> findByActifTrue();
}
