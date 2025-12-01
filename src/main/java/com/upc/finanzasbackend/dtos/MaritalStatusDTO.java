package com.upc.finanzasbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class MaritalStatusDTO {
    private Long maritalStatusId;
    private String statusName;
}
