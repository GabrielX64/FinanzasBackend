package com.upc.finanzasbackend.repositories;

import com.upc.finanzasbackend.entities.Loan;
import com.upc.finanzasbackend.entities.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {
    public List<LoanInstallment> findByLoan_LoanID(Long loanID);
}