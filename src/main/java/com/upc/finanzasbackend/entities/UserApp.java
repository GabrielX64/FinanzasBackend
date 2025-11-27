package com.upc.finanzasbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userAppID;
    private String names;
    private String surnames;
    private String username;
    private String password;
}
