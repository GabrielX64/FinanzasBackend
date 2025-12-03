package com.upc.finanzasbackend.security.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAppID;
    private String names;
    private String surnames;
    private String email;
    private String username;
    private String password;
    @ManyToOne
    @JoinColumn (name = "roleID")
    private Role role;
}