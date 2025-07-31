package com.rivaldy.orderapp.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemPaginationRequest extends BasePaginationRequest{

    private String name;
    private BigDecimal price;
}
