package com.upc.finanzasbackend.security.repositories;

import com.upc.finanzasbackend.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    public Role findById(long id);
}
