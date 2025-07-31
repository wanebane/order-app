package com.rivaldy.orderapp.repository;

import com.rivaldy.orderapp.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long>,
        JpaSpecificationExecutor<Inventory> {

    @Query("SELECT v " +
            "FROM Inventory v JOIN v.item vi " +
            "WHERE vi.id = :itemId")
    Optional<Inventory> findByItem(@Param("itemId") Long itemId);
}
