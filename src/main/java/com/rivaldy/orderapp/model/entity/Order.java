package com.rivaldy.orderapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "order_no", length = 4, nullable = false)
    private String orderNo;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @Column(name = "qty", nullable = false)
    private Integer qty;
    @Column(name = "price", precision = 20, scale = 2, nullable = false)
    private BigDecimal price;
}
