package com.rivaldy.orderapp.model.request;

import lombok.Data;

@Data
public class InventoryUpdateRequest {
    private Long itemId;
    private Integer qty;
    private String type;
}
