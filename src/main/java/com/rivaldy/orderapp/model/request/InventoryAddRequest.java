package com.rivaldy.orderapp.model.request;

import com.rivaldy.orderapp.validator.annotation.ValidInventoryType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InventoryAddRequest {

    @NotNull(message = "itemId cannot be empty")
    @Positive(message = "itemId must input with positive number")
    private Long itemId;

    @NotNull(message = "qty cannot be empty")
    @Min(value = 1, message = "qty must be at least 1")
    private Integer qty;

    @NotNull(message = "Type cannot be null")
    @ValidInventoryType
    private String type;
}
