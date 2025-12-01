package com.upc.finanzasbackend.controllers;
import com.upc.finanzasbackend.dtos.EstadoCivilDTO;
import com.upc.finanzasbackend.dtos.MaritalStatusDTO;
import com.upc.finanzasbackend.entities.EstadoCivil;
import com.upc.finanzasbackend.entities.MaritalStatus;
import com.upc.finanzasbackend.repositories.EstadoCivilRepository;
import com.upc.finanzasbackend.repositories.MaritalStatusRepository;
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
public class MaritalStatusController {
    @Autowired
    private MaritalStatusRepository maritalStatusRepository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping("/estados-civiles")
    public List<MaritalStatusDTO> getAllEstadosCiviles() {
        List<MaritalStatus> estados = maritalStatusRepository.findAll();
        return Arrays.asList(mapper.map(estados, MaritalStatusDTO[].class));
    }

    @GetMapping("/estado-civil/{id}")
    public ResponseEntity<MaritalStatusDTO> getEstadoCivilById(@PathVariable Long id) {
        MaritalStatus status = maritalStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado civil no encontrado"));
        return ResponseEntity.ok(mapper.map(status, MaritalStatusDTO.class));
    }
}
