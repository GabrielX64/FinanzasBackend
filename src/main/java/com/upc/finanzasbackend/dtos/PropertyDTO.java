package com.upc.finanzasbackend.dtos;

import com.upc.finanzasbackend.entities.Currency;
import com.upc.finanzasbackend.entities.PropertyStatus;
import com.upc.finanzasbackend.entities.PropertyType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropertyDTO {
    private Long propertyID;
    private String code;
    private String address;
    private Double areaM2;
    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal salePrice;
    private PropertyType type;
    private Currency currency;
    private PropertyStatus status;
}