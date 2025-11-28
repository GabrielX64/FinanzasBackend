package com.upc.finanzasbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanInstallment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private Integer period;
    private BigDecimal initialBalance;
    private BigDecimal interest;
    private BigDecimal amortization;
    private BigDecimal fee;
    private BigDecimal finalBalance;
    private BigDecimal cashFlow;

}
