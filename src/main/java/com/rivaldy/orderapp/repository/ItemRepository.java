package com.rivaldy.orderapp.repository;

import com.rivaldy.orderapp.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>,
        JpaSpecificationExecutor<Item> {
}
