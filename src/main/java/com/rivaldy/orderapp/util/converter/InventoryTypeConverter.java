package com.rivaldy.orderapp.util.converter;

import com.rivaldy.orderapp.util.enumerate.InventoryType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InventoryTypeConverter implements AttributeConverter<InventoryType, String> {
    @Override
    public String convertToDatabaseColumn(InventoryType inventoryType) {
        return inventoryType.getType();
    }

    @Override
    public InventoryType convertToEntityAttribute(String type) {
        return InventoryType.fromType(type);
    }
}
