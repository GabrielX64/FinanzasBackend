package com.upc.finanzasbackend.security.services;

import com.upc.finanzasbackend.security.entities.Role;
import com.upc.finanzasbackend.security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    // Obtener todos los roles
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    // Obtener rol por ID
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    // Crear o actualizar rol
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    // Eliminar rol por ID
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

}
