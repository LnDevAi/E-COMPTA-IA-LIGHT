package com.ecomptaia.accounting.repository;

import com.ecomptaia.accounting.entity.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, UUID> {
}
