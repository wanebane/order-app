package com.rivaldy.orderapp.model.request;

import com.rivaldy.orderapp.validator.annotation.ValidInventoryType;
import lombok.Data;

@Data
public class InventoryUpdateRequest {
    private Long itemId;
    private Integer qty;
    @ValidInventoryType
    private String type;
}
