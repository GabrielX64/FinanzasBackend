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
        log.info("Creando préstamo para cliente ID: {}", dto.getClientID());
        return loanService.createFrenchLoan(dto);
    }

    @GetMapping("/loan/{loanID}/schedule")
    public List<LoanInstallmentDTO> getSchedule(@PathVariable Long loanID) {
        return loanService.getScheduleByLoanId(loanID);
    }

    @GetMapping("/loan/cliente/{clienteID}")
    public List<Loan> getLoansByCliente(@PathVariable Long clienteID) {
        log.info("Obteniendo préstamos del cliente ID: {}", clienteID);
        return loanService.getLoanByClient(clienteID);
    }

    @GetMapping("/loan/asesor/{asesorID}")
    public List<Loan> getLoansByAsesor(@PathVariable Long asesorID) {
        log.info("Obteniendo préstamos gestionados por asesor ID: {}", asesorID);
        return loanService.getLoansByUser(asesorID);
    }
}
