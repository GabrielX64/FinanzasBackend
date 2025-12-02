package com.upc.finanzasbackend.Interfaces;
import com.upc.finanzasbackend.entities.Property;
import java.util.List;

public interface IPropertyService {
    List<Property> getAllProperties();
    Property getPropertyById(Long id);
    Property createProperty(Property property);
    Property updateProperty(Property property);
    void deleteProperty(Long id);
    List<Property> getPropertiesByStatus(Long statusId);
    List<Property> getPropertiesByType(Long typeId);
    List<Property> getAvailableProperties();
}
