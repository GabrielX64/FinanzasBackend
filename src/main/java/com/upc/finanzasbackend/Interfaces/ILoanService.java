package com.upc.finanzasbackend.Interfaces;

import com.upc.finanzasbackend.dtos.LoanInstallmentDTO;
import com.upc.finanzasbackend.dtos.LoanRequestDTO;
import com.upc.finanzasbackend.dtos.LoanResponseDTO;
import com.upc.finanzasbackend.entities.Loan;

import java.util.List;

public interface ILoanService {
    public LoanResponseDTO createFrenchLoan(LoanRequestDTO dto);
    public List<LoanInstallmentDTO> getScheduleByLoanId(Long loanID);
    public List<Loan> getLoansByUser(Long userID);
}