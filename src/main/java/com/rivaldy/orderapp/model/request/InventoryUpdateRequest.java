package com.rivaldy.orderapp.model.request;

import com.rivaldy.orderapp.validator.annotation.ValidInventoryType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InventoryUpdateRequest {
    @NotNull(message = "itemId cannot be empty")
    @Positive(message = "itemId must input with positive number")
    private Long itemId;
    @Min(value = 1, message = "qty must be at least 1")
    private Integer qty;
    @ValidInventoryType
    private String type;
}
