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

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientID;

    private String names;
    private String surnames;

    @Column(unique = true, length = 8)
    private String dni;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    private BigDecimal monthlyIncome;
    private String occupation;

    // Relación ManyToOne con EstadoCivil
    @ManyToOne
    @JoinColumn(name = "maritalStatus")
    private MaritalStatus maritalStatus;

    private String currentAddress;

    // Un cliente puede tener múltiples préstamos
    // La relación inversa se define en Loan
}
