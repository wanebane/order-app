package com.rivaldy.orderapp.model.request;

import lombok.Data;

@Data
public class OrderUpdateRequest {
    private Long itemId;
    private Integer qty;
}
