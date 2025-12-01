package com.upc.finanzasbackend.controllers;

import com.upc.finanzasbackend.dtos.FinancialEntityDTO;
import com.upc.finanzasbackend.entities.FinancialEntity;
import com.upc.finanzasbackend.repositories.EntityFinancialRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","http://18.223.169.236/"})
@RestController
@RequestMapping("/api")
@Slf4j

public class FinancialEntityController {
    @Autowired
    private EntityFinancialRepository financialRepository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping("/entidades-financieras")
    public List<FinancialEntityDTO> getAllEntidades() {
        List<FinancialEntity> entidades = financialRepository.findAll();
        return Arrays.asList(mapper.map(entidades, FinancialEntityDTO[].class));
    }

    @GetMapping("/entidades-financieras/activas")
    public List<FinancialEntityDTO> getEntidadesActivas() {
        List<FinancialEntity> entidades = financialRepository.findByActiveTrue();
        return Arrays.asList(mapper.map(entidades, FinancialEntityDTO[].class));
    }

    @GetMapping("/entidad-financiera/{id}")
    public ResponseEntity<FinancialEntityDTO> getEntidadById(@PathVariable Long id) {
        FinancialEntity entidad = financialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entidad financiera no encontrada"));
        return ResponseEntity.ok(mapper.map(entidad, FinancialEntityDTO.class));
    }

    @PostMapping("/entidad-financiera")
    public ResponseEntity<FinancialEntityDTO> createEntidad(@RequestBody FinancialEntityDTO dto) {
        FinancialEntity entidad = mapper.map(dto, FinancialEntity.class);
        entidad = financialRepository.save(entidad);
        return ResponseEntity.ok(mapper.map(entidad, FinancialEntityDTO.class));
    }

    @PutMapping("/entidad-financiera/update")
    public ResponseEntity<FinancialEntityDTO> updateEntidad(@RequestBody FinancialEntityDTO dto) {
        FinancialEntity entidad = mapper.map(dto, FinancialEntity.class);
        entidad = financialRepository.save(entidad);
        return ResponseEntity.ok(mapper.map(entidad, FinancialEntityDTO.class));
    }

    @DeleteMapping("/entidad-financiera/delete/{id}")
    public ResponseEntity<String> deleteEntidad(@PathVariable Long id) {
        financialRepository.deleteById(id);
        return ResponseEntity.ok("Entidad financiera eliminada correctamente");
    }
}
