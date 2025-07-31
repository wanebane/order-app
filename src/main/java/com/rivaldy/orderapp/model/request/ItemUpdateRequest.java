package com.rivaldy.orderapp.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemUpdateRequest {
    @NotNull
    @Size(min = 3, max = 50, message = "name input max 50 characters")
    private String name;
    @DecimalMin(value = "0.0")
    private BigDecimal price;
}
