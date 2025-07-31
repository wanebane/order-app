package com.rivaldy.orderapp.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderPaginationRequest extends BasePaginationRequest{

    private Long itemId;
    private Integer qty;
    private BigDecimal price;
}
