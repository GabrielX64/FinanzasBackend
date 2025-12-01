package com.upc.finanzasbackend.repositories;
import com.upc.finanzasbackend.entities.FinancialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntityFinancialRepository extends JpaRepository<FinancialEntity, Long> {
    Optional<FinancialEntity> findByCode(String code);
    List<FinancialEntity> findByActiveTrue();
    boolean existsByCode(String code);
}
