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
public class LoanRequestDTO {
    private Long clientID;
    private Long asesorID;
    private Long financialEntityID;
    // Monto y plazo
    private BigDecimal principal; // Monto después de restar la cuota inicial
    private Integer years;
    private Integer totalGrace;
    private Integer partialGrace;

    private Long propertyId;

    // --- TASA ---
    private String rateType;                 // "TEA" o "TNP"

    private BigDecimal tea;                  // solo si rateType = "TEA"
    private BigDecimal tnp;                  // solo si rateType = "TNP"
    private Long capitalizationFrequencyID;

    // Rentabilidad/COK
    private BigDecimal cok;

    // Precio de la vivienda (antes de la cuota inicial)
    private BigDecimal propertyPrice;
}
