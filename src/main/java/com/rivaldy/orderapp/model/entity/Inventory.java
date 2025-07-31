package com.rivaldy.orderapp.model.entity;

import com.rivaldy.orderapp.util.converter.InventoryTypeConverter;
import com.rivaldy.orderapp.util.enumerate.InventoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @Column(name = "qty", nullable = false)
    private Integer qty;
    @Convert(converter = InventoryTypeConverter.class)
    @Column(name = "type", length = 1, nullable = false)
    private InventoryType type;
}
