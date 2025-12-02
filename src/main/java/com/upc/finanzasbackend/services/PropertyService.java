package com.upc.finanzasbackend.services;

import com.upc.finanzasbackend.Interfaces.IPropertyService;
import com.upc.finanzasbackend.entities.Property;
import com.upc.finanzasbackend.exceptions.RequestException;
import com.upc.finanzasbackend.repositories.PropertyRepository;
import com.upc.finanzasbackend.util.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService implements IPropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RequestException("P001",
                        HttpStatus.NOT_FOUND,
                        "Propiedad no encontrada"));
    }

    @Override
    public Property createProperty(Property property) {
        // Validaciones básicas
        validationService.verifyNoNulls(
                property.getCode(),
                property.getAddress(),
                property.getSalePrice(),
                property.getType(),
                property.getCurrency(),
                property.getStatus()
        );

        validationService.verifyNoEmpty(
                property.getCode(),
                property.getAddress()
        );

        // Validar que el código sea único
        propertyRepository.findAll().stream()
                .filter(p -> p.getCode().equals(property.getCode()))
                .findFirst()
                .ifPresent(p -> {
                    throw new RequestException("P002",
                            HttpStatus.CONFLICT,
                            "El código de propiedad ya existe");
                });

        return propertyRepository.save(property);
    }

    @Override
    public Property updateProperty(Property property) {
        Property existingProperty = propertyRepository.findById(property.getPropertyID())
                .orElseThrow(() -> new RequestException("P001",
                        HttpStatus.NOT_FOUND,
                        "Propiedad no encontrada"));

        // Validar código único (excluyendo la propiedad actual)
        propertyRepository.findAll().stream()
                .filter(p -> p.getCode().equals(property.getCode())
                        && !p.getPropertyID().equals(property.getPropertyID()))
                .findFirst()
                .ifPresent(p -> {
                    throw new RequestException("P002",
                            HttpStatus.CONFLICT,
                            "El código de propiedad ya existe");
                });

        return propertyRepository.save(property);
    }

    @Override
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new RequestException("P001",
                    HttpStatus.NOT_FOUND,
                    "Propiedad no encontrada");
        }
        propertyRepository.deleteById(id);
    }

    @Override
    public List<Property> getPropertiesByStatus(Long statusId) {
        return propertyRepository.findAll().stream()
                .filter(p -> p.getStatus().getStatusID().equals(statusId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Property> getPropertiesByType(Long typeId) {
        return propertyRepository.findAll().stream()
                .filter(p -> p.getType().getPropertyTypeID().equals(typeId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Property> getAvailableProperties() {
        return propertyRepository.findAll().stream()
                .filter(p -> p.getStatus().getName().equalsIgnoreCase("Disponible"))
                .collect(Collectors.toList());
    }
}
