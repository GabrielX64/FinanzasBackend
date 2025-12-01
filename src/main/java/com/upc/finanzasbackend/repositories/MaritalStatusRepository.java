package com.upc.finanzasbackend.repositories;
import com.upc.finanzasbackend.entities.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long>  {
    Optional<MaritalStatus> findByStatusName(String statusName);
}
