package com.upc.finanzasbackend.controllers;
import com.upc.finanzasbackend.Interfaces.IPropertyService;
import com.upc.finanzasbackend.dtos.PropertyDTO;
import com.upc.finanzasbackend.entities.Property;
import com.upc.finanzasbackend.entities.Currency;
import com.upc.finanzasbackend.entities.PropertyStatus;
import com.upc.finanzasbackend.entities.PropertyType;
import com.upc.finanzasbackend.repositories.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","http://18.223.169.236/"})
@RestController
@RequestMapping("/api")
@Slf4j
public class PropertyController {

    @Autowired
    private IPropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    private final ModelMapper mapper = new ModelMapper();

    // Método auxiliar para convertir Entity a DTO
    private PropertyDTO toDTO(Property property) {
        PropertyDTO dto = new PropertyDTO();
        dto.setPropertyID(property.getPropertyID());
        dto.setCode(property.getCode());
        dto.setAddress(property.getAddress());
        dto.setAreaM2(property.getAreaM2());
        dto.setBedrooms(property.getBedrooms());
        dto.setBathrooms(property.getBathrooms());
        dto.setSalePrice(property.getSalePrice());
        dto.setType(property.getType());
        dto.setCurrency(property.getCurrency());
        dto.setStatus(property.getStatus());
        return dto;
    }

    // Método auxiliar para convertir DTO a Entity
    private Property toEntity(PropertyDTO dto) {
        Property property = new Property();
        property.setPropertyID(dto.getPropertyID());
        property.setCode(dto.getCode());
        property.setAddress(dto.getAddress());
        property.setAreaM2(dto.getAreaM2());
        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setSalePrice(dto.getSalePrice());
        property.setType(dto.getType());
        property.setCurrency(dto.getCurrency());
        property.setStatus(dto.getStatus());
        return property;
    }

    @GetMapping("/propiedades")
    @PreAuthorize("hasRole('USER')")
    public List<PropertyDTO> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return properties.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/propiedad/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        Property property = propertyService.getPropertyById(id);
        return ResponseEntity.ok(toDTO(property));
    }

    @GetMapping("/propiedades/disponibles")
    public List<PropertyDTO> getAvailableProperties() {
        List<Property> properties = propertyService.getAvailableProperties();
        return properties.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/propiedades/estado/{statusId}")
    public List<PropertyDTO> getPropertiesByStatus(@PathVariable Long statusId) {
        List<Property> properties = propertyService.getPropertiesByStatus(statusId);
        return properties.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/propiedades/tipo/{typeId}")
    public List<PropertyDTO> getPropertiesByType(@PathVariable Long typeId) {
        List<Property> properties = propertyService.getPropertiesByType(typeId);
        return properties.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/propiedad")
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        Property property = toEntity(propertyDTO);
        property = propertyService.createProperty(property);
        return ResponseEntity.ok(toDTO(property));
    }

    @PutMapping("/propiedad/update")
    public ResponseEntity<PropertyDTO> updateProperty(@RequestBody PropertyDTO propertyDTO) {
        Property property = toEntity(propertyDTO);
        property = propertyService.updateProperty(property);
        return ResponseEntity.ok(toDTO(property));
    }

    @DeleteMapping("/propiedad/delete/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.ok("Propiedad eliminada correctamente");
    }
}
