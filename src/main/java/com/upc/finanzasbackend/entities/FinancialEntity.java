package com.upc.finanzasbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

public class FinancialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long financialEntityID;

    private String name;
    private String code; // BCP, BBVA, INTERBANK, SCOTIABANK

    // Porcentaje de cuota inicial que cubre (entre 7.5% y 11%)
    private BigDecimal downPaymentPercentage;

    private boolean active = true;
}
