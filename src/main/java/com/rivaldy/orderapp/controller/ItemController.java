package com.rivaldy.orderapp.controller;

import com.rivaldy.orderapp.model.request.ItemPaginationRequest;
import com.rivaldy.orderapp.model.request.ItemAddRequest;
import com.rivaldy.orderapp.model.request.ItemUpdateRequest;
import com.rivaldy.orderapp.model.response.GeneralResponse;
import com.rivaldy.orderapp.service.ItemService;
import com.rivaldy.orderapp.util.constant.AppConstant;
import com.rivaldy.orderapp.util.mapper.FormatMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final FormatMapper mapper = new FormatMapper();
    private final AppConstant constant = new AppConstant();

    @GetMapping
    public ResponseEntity<GeneralResponse> page(@Valid ItemPaginationRequest request){
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_PAGE, constant.LAB_ITEM),
                        itemService.getPage(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> detail(@PathVariable Long id){
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_DETAIL, constant.LAB_ITEM, id),
                        itemService.getDetail(id)));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> add(@Valid @RequestBody ItemAddRequest request){
        return new ResponseEntity<>(mapper
                .toGeneralResponse(String.format(constant.MSG_ADD, constant.LAB_ITEM),
                        itemService.addItem(request)),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> update(@PathVariable Long id, @Valid @RequestBody ItemUpdateRequest request){
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_EDIT, constant.LAB_ITEM, id),
                        itemService.updateItem(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@PathVariable Long id){
        itemService.deleteItem(id);
        return ResponseEntity.ok(mapper
                .toGeneralResponse(String.format(constant.MSG_DEL, constant.LAB_ITEM, id)));
    }
}