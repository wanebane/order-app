package com.rivaldy.orderapp.controller;

import com.rivaldy.orderapp.model.request.InventoryAddRequest;
import com.rivaldy.orderapp.model.request.InventoryPaginationRequest;
import com.rivaldy.orderapp.model.request.InventoryUpdateRequest;
import com.rivaldy.orderapp.model.response.GeneralResponse;
import com.rivaldy.orderapp.service.InventoryService;
import com.rivaldy.orderapp.util.constant.AppConstant;
import com.rivaldy.orderapp.util.mapper.FormatMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventories")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final FormatMapper mapper = new FormatMapper();
    private final AppConstant constant = new AppConstant();

    @GetMapping
    public ResponseEntity<GeneralResponse> page(@Valid InventoryPaginationRequest request){
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_PAGE, constant.LAB_INVENTORY),
                        inventoryService.getPage(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> detail(@PathVariable Long id){
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_DETAIL, constant.LAB_INVENTORY, id),
                        inventoryService.getDetail(id)));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> add(@Valid @RequestBody InventoryAddRequest request){
        return new ResponseEntity<>(mapper
                .toGeneralResponse(String.format(constant.MSG_ADD, constant.LAB_INVENTORY),
                        inventoryService.addInventory(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> update(@PathVariable Long id, @Valid @RequestBody InventoryUpdateRequest request){
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_EDIT, constant.LAB_INVENTORY, id),
                        inventoryService.updateInventory(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@PathVariable Long id){
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_DEL, constant.LAB_INVENTORY, id)));
    }
}
