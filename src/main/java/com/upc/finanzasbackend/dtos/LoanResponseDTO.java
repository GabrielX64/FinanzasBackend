package com.upc.finanzasbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanResponseDTO {
    private Long loanId;
    private BigDecimal tir;
    private BigDecimal tcea;
    private BigDecimal van;
    private List<LoanInstallmentDTO> schedule;

    // NUEVO: Información sobre cuota inicial
    private BigDecimal propertyPrice;
    private BigDecimal downPayment;
    private BigDecimal downPaymentPercentage;
    private BigDecimal financedAmount; // = precioVivienda - cuotaInicial
    private String financialEntity;

}
