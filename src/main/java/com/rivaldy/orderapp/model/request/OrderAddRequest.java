package com.rivaldy.orderapp.model.request;

import lombok.Data;

@Data
public class OrderAddRequest {

    private Long itemId;
    private Integer qty;
}
