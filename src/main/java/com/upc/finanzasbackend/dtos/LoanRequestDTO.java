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
    private Long userID;
    // Monto y plazo
    private BigDecimal principal;
    private Integer years;
    private Integer totalGrace;
    private Integer partialGrace;

    // --- TASA ---
    private String rateType;                 // "TEA" o "TNP"

    private BigDecimal tea;                  // solo si rateType = "TEA"
    private BigDecimal tnp;                  // solo si rateType = "TNP"
    private Integer capitalizationsPerYear;  // m: ej. 12, 6, 4...

    // Rentabilidad/COK
    private BigDecimal cok;
}
