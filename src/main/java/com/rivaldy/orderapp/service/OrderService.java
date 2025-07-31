package com.rivaldy.orderapp.service;

import com.rivaldy.orderapp.exception.DataNotFoundException;
import com.rivaldy.orderapp.model.entity.Item;
import com.rivaldy.orderapp.model.entity.Order;
import com.rivaldy.orderapp.model.request.OrderAddRequest;
import com.rivaldy.orderapp.model.request.OrderPaginationRequest;
import com.rivaldy.orderapp.model.request.OrderUpdateRequest;
import com.rivaldy.orderapp.model.response.OrderResponse;
import com.rivaldy.orderapp.model.response.PageResponse;
import com.rivaldy.orderapp.repository.OrderRepository;
import com.rivaldy.orderapp.util.constant.AppConstant;
import com.rivaldy.orderapp.util.specification.EntitySpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final EntitySpecification entitySpecification = new EntitySpecification();
    private final AppConstant constant = new AppConstant();

    public OrderResponse toResponse(Order order){
        return OrderResponse.builder()
                .item(itemService.toResponse(order.getItem()))
                .orderNo(order.getOrderNo())
                .qty(order.getQty())
                .price(order.getPrice())
                .build();
    }

    public OrderResponse toResponseId(Order order){
        OrderResponse response = toResponse(order);
        response.setId(order.getId());
        return response;
    }

    public Order findOrder(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format(constant.ERR_NOT_FOUND, constant.LAB_ORDER, id)));
    }

    public PageResponse<OrderResponse> getPage(OrderPaginationRequest request){
        Specification<Order> spec = entitySpecification.filter(request);
        Page<Order> page = orderRepository.findAll(spec, request.toPageable());

        return PageResponse.<OrderResponse>builder()
                .content(page.getContent().stream()
                        .map(this::toResponseId)
                        .collect(Collectors.toList()))
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .pageSize(page.getSize())
                .build();
    }

    public OrderResponse getDetail(Long id){
        return toResponse(findOrder(id));
    }

    @Transactional
    public OrderResponse addOrder(OrderAddRequest request){
        Item item = itemService.findItem(request.getItemId());
        inventoryService.reduceStock(request.getItemId(), request.getQty());

        int maxSequence = orderRepository.findMaxOrderNoSequence();
        int nextSequence = (maxSequence == 0) ? maxSequence + 1 : 1;

        Order order = Order.builder()
                .item(item)
                .orderNo("O"+nextSequence)
                .qty(request.getQty())
                .price(item.getPrice().multiply(BigDecimal.valueOf(request.getQty())))
                .build();
        orderRepository.save(order);
        return toResponseId(order);
    }

    public OrderResponse updateOrder(Long id, OrderUpdateRequest request){
        Order order = findOrder(id);
        Item item = itemService.findItem(request.getItemId());
        if (!Objects.equals(request.getItemId(), order.getItem().getId())){
            inventoryService.reStock(order.getItem().getId(), order.getQty());
        }
        inventoryService.reduceStock(request.getItemId(), request.getQty());
        order.setItem(item);
        order.setQty(request.getQty());
        order.setPrice(item.getPrice().multiply(BigDecimal.valueOf(request.getQty())));
        orderRepository.save(order);
        return toResponse(order);
    }

    public void deleteOrder(Long id){
        if (!orderRepository.existsById(id)){
            throw new DataNotFoundException(String.format(constant.ERR_NOT_FOUND, constant.LAB_ORDER, id));
        }
        orderRepository.deleteById(id);
    }
}
