package com.rivaldy.orderapp.util.enumerate;

import lombok.Getter;

import java.util.stream.Stream;

public enum InventoryType {
    TOP_UP("T", "Top Up"),
    WITHDRAWAL("W", "Withdrawal");

    @Getter
    private final String type;

    @Getter
    private final String desc;

    InventoryType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static InventoryType fromType(String type) {
        return Stream.of(values())
                .filter(t -> t.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid type: " + type));
    }
}
