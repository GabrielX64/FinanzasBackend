package com.upc.finanzasbackend.repositories;
import com.upc.finanzasbackend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByDni(String dni);
    Optional<Client> findByEmail(String email);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
}
