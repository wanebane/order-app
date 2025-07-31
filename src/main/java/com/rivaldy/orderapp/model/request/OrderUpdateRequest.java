package com.rivaldy.orderapp.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderUpdateRequest {
    @NotNull(message = "itemId cannot be empty")
    @Positive(message = "itemId must input with positive number")
    private Long itemId;
    @NotNull(message = "qty cannot be empty")
    @Min(value = 1, message = "qty must be at least 1")
    private Integer qty;
}
