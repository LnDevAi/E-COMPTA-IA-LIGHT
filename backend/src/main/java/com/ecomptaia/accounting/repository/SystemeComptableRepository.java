package com.ecomptaia.accounting.repository;

import com.ecomptaia.accounting.entity.SystemeComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemeComptableRepository extends JpaRepository<SystemeComptable, Long> {
    SystemeComptable findByCode(String code);
}
