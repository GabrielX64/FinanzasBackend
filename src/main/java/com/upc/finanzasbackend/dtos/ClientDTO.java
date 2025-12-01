package com.upc.finanzasbackend.dtos;

import com.upc.finanzasbackend.entities.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ClientDTO {
    private Long clientID;
    private String names;
    private String surnames;
    private String dni;
    private String email;
    private String phoneNumber;
    private BigDecimal monthlyIncome;
    private String occupation;
    private Long maritalStatusID; // Cambiado a ID
    private String currentAddress;
}
