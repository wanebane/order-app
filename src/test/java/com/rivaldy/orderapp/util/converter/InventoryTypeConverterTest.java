package com.rivaldy.orderapp.util.converter;

import com.rivaldy.orderapp.util.enumerate.InventoryType;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class InventoryTypeConverterTest {

    private final InventoryTypeConverter converter = new InventoryTypeConverter();

    @Test
    void convertToDatabaseColumn_shouldReturnTypeCode() {
        assertThat(converter.convertToDatabaseColumn(InventoryType.TOP_UP)).isEqualTo("T");
        assertThat(converter.convertToDatabaseColumn(InventoryType.WITHDRAWAL)).isEqualTo("W");
    }

    @Test
    void convertToEntityAttribute_shouldReturnCorrectEnum() {
        assertThat(converter.convertToEntityAttribute("T")).isEqualTo(InventoryType.TOP_UP);
        assertThat(converter.convertToEntityAttribute("W")).isEqualTo(InventoryType.WITHDRAWAL);
    }

    @Test
    void convertToEntityAttribute_withInvalidType_shouldThrowException() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute("X"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid type: X");
    }
}