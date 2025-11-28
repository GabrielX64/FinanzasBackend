package com.upc.finanzasbackend.repositories;

import com.upc.finanzasbackend.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    public List<Loan> findByUserID_UserAppID(Long userID);
}
