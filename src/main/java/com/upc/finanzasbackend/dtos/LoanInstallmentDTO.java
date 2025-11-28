package com.upc.finanzasbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanInstallmentDTO {
    private Integer period;
    private BigDecimal initialBalance;
    private BigDecimal interest;
    private BigDecimal amortization;
    private BigDecimal fee;
    private BigDecimal finalBalance;
    private BigDecimal cashFlow;
}
