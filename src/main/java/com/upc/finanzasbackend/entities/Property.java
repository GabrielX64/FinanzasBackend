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
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyID;

    private String code;
    private String address;
    private Double areaM2;
    private Integer bedrooms;
    private Integer bathrooms;

    private BigDecimal salePrice;

    @ManyToOne
    @JoinColumn(name = "property_type_id", nullable = false)
    private PropertyType type;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private PropertyStatus status;
}