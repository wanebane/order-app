package com.rivaldy.orderapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String name;
    private BigDecimal price;
}
