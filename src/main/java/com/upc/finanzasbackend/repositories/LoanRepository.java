package com.upc.finanzasbackend.repositories;

import com.upc.finanzasbackend.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    // Buscar préstamos por cliente
    List<Loan> findByClientID_ClientID(Long clientID);

    // Buscar préstamos por asesor (opcional, para reportes)
    List<Loan> findByAsesor_UserAppID(Long asesorID);

    // Buscar préstamos por entidad financiera
    List<Loan> findByFinancialEntity_FinancialEntityID(Long entidadFinancieraID);

}
