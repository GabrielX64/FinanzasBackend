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

public class FinancialEntityDTO {
    private Long financialEntityID;
    private String name;
    private String code;
    private BigDecimal downPaymentPercentage;
    private boolean active;
}
