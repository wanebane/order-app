package com.rivaldy.orderapp.util.enumerate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTypeTest {

    @Test
    void fromType_shouldReturnCorrectEnum() {
        assertThat(InventoryType.fromType("T")).isEqualTo(InventoryType.TOP_UP);
        assertThat(InventoryType.fromType("W")).isEqualTo(InventoryType.WITHDRAWAL);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> InventoryType.fromType("X")
        );
        assertThat(exception.getMessage()).contains("Invalid type: X");
    }

    @Test
    void getter_shouldReturnCorrectValue() {
        assertThat(InventoryType.TOP_UP.getType()).isEqualTo("T");
        assertThat(InventoryType.TOP_UP.getDesc()).isEqualTo("Top Up");

        assertThat(InventoryType.WITHDRAWAL.getType()).isEqualTo("W");
        assertThat(InventoryType.WITHDRAWAL.getDesc()).isEqualTo("Withdrawal");
    }
}