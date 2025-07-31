package com.rivaldy.orderapp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemAddRequest {
    @NotBlank(message = "name cannot be empty")
    @Size(min = 3, max = 50, message = "name must input 5-50 characters")
    private String name;
    @NotNull(message = "price cannot be empty")
    private BigDecimal price;
}
