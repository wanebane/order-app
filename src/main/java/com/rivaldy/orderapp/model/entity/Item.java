package com.rivaldy.orderapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "price", precision = 20, scale = 2, nullable = false)
    private BigDecimal price;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Inventory> inventories = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<Order> orders = new ArrayList<>();
}
