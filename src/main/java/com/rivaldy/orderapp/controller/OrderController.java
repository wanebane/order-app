package com.rivaldy.orderapp.controller;

import com.rivaldy.orderapp.model.request.OrderAddRequest;
import com.rivaldy.orderapp.model.request.OrderPaginationRequest;
import com.rivaldy.orderapp.model.request.OrderUpdateRequest;
import com.rivaldy.orderapp.model.response.GeneralResponse;
import com.rivaldy.orderapp.service.OrderService;
import com.rivaldy.orderapp.util.constant.AppConstant;
import com.rivaldy.orderapp.util.mapper.FormatMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final FormatMapper mapper = new FormatMapper();
    private final AppConstant constant = new AppConstant();

    @GetMapping
    public ResponseEntity<GeneralResponse> page(@Valid OrderPaginationRequest request){
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_PAGE, constant.LAB_ORDER),
                        orderService.getPage(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> detail(@PathVariable Long id){
        return ResponseEntity.ok(mapper.toGeneralResponse(String.format(constant.MSG_DETAIL, constant.LAB_ORDER, id),
                orderService.getDetail(id)));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> add(@Valid @RequestBody OrderAddRequest request){
        return new ResponseEntity<>(mapper.toGeneralResponse(String.format(constant.MSG_ADD, constant.LAB_ORDER),
                orderService.addOrder(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> update(@PathVariable Long id, @Valid @RequestBody OrderUpdateRequest request){
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_EDIT, constant.LAB_ORDER, id),
                        orderService.updateOrder(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_DEL, constant.LAB_ORDER, id)));
    }
}
