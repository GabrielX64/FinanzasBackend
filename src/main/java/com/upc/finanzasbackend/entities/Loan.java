package com.upc.finanzasbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserApp userID;

    private BigDecimal principal;          // monto del préstamo
    private BigDecimal tea;                // TEA (0.11 = 11%)

    // NUEVO: datos de tasa nominal
    private String rateType;              // "TEA" o "TNP"
    private BigDecimal tnp;              // puede ser null si es TEA

    @ManyToOne
    @JoinColumn(name = "capitalization_frequency_id")
    private CapitalizationFrequency capitalizationFrequency;

    private Integer years;                 // años
    private Integer frequencyPerYear;      // cuotas por año
    private Integer totalGrace;            // cuotas
    private Integer partialGrace;          // cuotas
    private BigDecimal cok;                // costo oportunidad

    private BigDecimal tir;                // opcional
    private BigDecimal tcea;               // opcional
    private BigDecimal van;                // opcional

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}
