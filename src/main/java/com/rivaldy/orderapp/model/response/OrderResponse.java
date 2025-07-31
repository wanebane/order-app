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
public class OrderResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String orderNo;
    private ItemResponse item;
    private Integer qty;
    private BigDecimal price;
}
