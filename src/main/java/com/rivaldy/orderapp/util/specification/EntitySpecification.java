package com.rivaldy.orderapp.util.specification;

import com.rivaldy.orderapp.model.entity.Inventory;
import com.rivaldy.orderapp.model.entity.Item;
import com.rivaldy.orderapp.model.entity.Order;
import com.rivaldy.orderapp.model.request.InventoryPaginationRequest;
import com.rivaldy.orderapp.model.request.ItemPaginationRequest;
import com.rivaldy.orderapp.model.request.OrderPaginationRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntitySpecification {

    public Specification<Item> filter(ItemPaginationRequest request){
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(request.getName())){
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + request.getName().toLowerCase() + "%"
                ));
            }

            if (request.getPrice() != null){
                predicates.add(cb.equal(root.get("price"), request.getPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public Specification<Inventory> filter(InventoryPaginationRequest request){
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getItemId() != null){
                predicates.add(cb.equal(root.get("item"), request.getItemId()));
            }


            if (request.getQty() != null){
                predicates.add(cb.equal(root.get("qty"), request.getQty()));
            }

            if (StringUtils.hasText(request.getType())){
                predicates.add(cb.equal(root.get("type"), request.getType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public Specification<Order> filter(OrderPaginationRequest request){
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getItemId() != null){
                predicates.add(cb.equal(root.get("item"), request.getItemId()));
            }


            if (request.getQty() != null){
                predicates.add(cb.equal(root.get("qty"), request.getQty()));
            }

            if (request.getPrice() != null){
                predicates.add(cb.equal(root.get("price"), request.getPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

}
