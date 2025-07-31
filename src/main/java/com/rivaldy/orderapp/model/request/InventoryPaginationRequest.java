package com.rivaldy.orderapp.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryPaginationRequest extends BasePaginationRequest{

    private Long itemId;
    private Integer qty;
    private String type;
}
