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
public class BonoMiViviendaDTO {
private boolean applies;
private String reason;
private String tipobonusType; // "BUEN PAGADOR" o "FAMILIAR"
private BigDecimal bonusAmount;
private BigDecimal propertyPrice;
private BigDecimal monthlyIncome;
}