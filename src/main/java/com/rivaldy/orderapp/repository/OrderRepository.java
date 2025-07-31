package com.rivaldy.orderapp.repository;

import com.rivaldy.orderapp.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(o.orderNo, 2) AS integer)), 0) " +
            "FROM Order o " +
            "WHERE o.orderNo LIKE 'O%'")
    Integer findMaxOrderNoSequence();
//    @Query(value = "SELECT COALESCE(MAX(CAST(SUBSTRING(o.order_no, 2) AS INTEGER)), 0) " +
//        "FROM orders o " +
//        "WHERE o.order_no LIKE 'O%'",
//        nativeQuery = true)
//    Integer findMaxOrderNoSequence();
}
