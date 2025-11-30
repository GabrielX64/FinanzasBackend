package com.upc.finanzasbackend.repositories;

import com.upc.finanzasbackend.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
