package com.upc.finanzasbackend.controllers;

import com.upc.finanzasbackend.Interfaces.ILoanService;
import com.upc.finanzasbackend.dtos.LoanInstallmentDTO;
import com.upc.finanzasbackend.dtos.LoanRequestDTO;
import com.upc.finanzasbackend.dtos.LoanResponseDTO;
import com.upc.finanzasbackend.entities.Loan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","http://18.223.169.236/"})
@RestController
@RequestMapping("/api")
@Slf4j
public class LoanController {
    @Autowired
    private ILoanService loanService;

    @PostMapping("/loan/french")
    public LoanResponseDTO createFrenchLoan(@RequestBody LoanRequestDTO dto) {
        return loanService.createFrenchLoan(dto);
    }

    @GetMapping("/loan/{loanID}/schedule")
    public List<LoanInstallmentDTO> getSchedule(@PathVariable Long loanID) {
        return loanService.getScheduleByLoanId(loanID);
    }

    @GetMapping("/loan/user/{userID}")
    public List<Loan> getLoansByUser(@PathVariable Long userID) {
        return loanService.getLoansByUser(userID);
    }
}
