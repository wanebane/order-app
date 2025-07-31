package com.rivaldy.orderapp.service;

import com.rivaldy.orderapp.exception.DataNotFoundException;
import com.rivaldy.orderapp.exception.InsufficientException;
import com.rivaldy.orderapp.model.entity.Inventory;
import com.rivaldy.orderapp.model.entity.Item;
import com.rivaldy.orderapp.model.request.InventoryAddRequest;
import com.rivaldy.orderapp.model.request.InventoryPaginationRequest;
import com.rivaldy.orderapp.model.request.InventoryUpdateRequest;
import com.rivaldy.orderapp.model.response.InventoryResponse;
import com.rivaldy.orderapp.model.response.PageResponse;
import com.rivaldy.orderapp.repository.InventoryRepository;
import com.rivaldy.orderapp.util.constant.AppConstant;
import com.rivaldy.orderapp.util.enumerate.InventoryType;
import com.rivaldy.orderapp.util.specification.EntitySpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemService itemService;
    private final EntitySpecification entitySpecification = new EntitySpecification();
    private final AppConstant constant = new AppConstant();

    public InventoryResponse toResponse(Inventory inventory){
        return InventoryResponse.builder()
                .item(itemService.toResponse(inventory.getItem()))
                .qty(inventory.getQty())
                .type(inventory.getType().getDesc())
                .build();
    }

    public InventoryResponse toResponseId(Inventory inventory){
        InventoryResponse response = toResponse(inventory);
        response.setId(inventory.getId());
        return response;
    }

    public Inventory findInventory(Long id){
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format(constant.ERR_NOT_FOUND, constant.LAB_INVENTORY, id)));
    }

    public PageResponse<InventoryResponse> getPage(InventoryPaginationRequest request){
        Specification<Inventory> spec = entitySpecification.filter(request);
        Page<Inventory> page = inventoryRepository.findAll(spec, request.toPageable());

        return PageResponse.<InventoryResponse>builder()
                .content(page.getContent().stream()
                        .map(this::toResponseId)
                        .collect(Collectors.toList()))
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .pageSize(page.getSize())
                .build();
    }

    public InventoryResponse getDetail(Long id){
        return toResponse(findInventory(id));
    }

    public InventoryResponse addInventory(InventoryAddRequest request){
        Item item = itemService.findItem(request.getItemId());

        Inventory inventory = Inventory.builder()
                .qty(request.getQty())
                .type(InventoryType.TOP_UP)
                .item(item)
                .build();

        inventoryRepository.save(inventory);

        return toResponseId(inventory);
    }

    public InventoryResponse updateInventory(Long id, InventoryUpdateRequest request){
        Item item = itemService.findItem(request.getItemId());
        Inventory inventory = findInventory(id);
        inventory.setItem(item);
        inventory.setType(InventoryType.fromType(request.getType()));
        proceedStock(inventory, request.getQty());
        inventoryRepository.save(inventory);
        return toResponse(inventory);
    }

    public void deleteInventory(Long id){
        if (!inventoryRepository.existsById(id)){
            throw new DataNotFoundException(String.format(constant.ERR_NOT_FOUND, constant.LAB_INVENTORY, id));
        }
        inventoryRepository.deleteById(id);
    }

    public void proceedStock(Inventory inventory, int reqQty){
        int currentQty = inventory.getQty();
        boolean isWithdrawal = InventoryType.WITHDRAWAL.equals(inventory.getType());
        boolean isStockIsNotEnough = currentQty == 0 || currentQty < reqQty;
        if (isWithdrawal && isStockIsNotEnough){
            throw new InsufficientException(String.format(constant.ERR_INS_STOCK, inventory.getItem().getId(), currentQty));
        }
        int lastQty = isWithdrawal ? currentQty - reqQty : currentQty + reqQty;
        inventory.setQty(lastQty);
    }

    public void reduceStock(Long itemId, Integer reqQty){
        Inventory inventory = inventoryRepository.findByItem(itemId)
                .orElseThrow(() -> new DataNotFoundException(String.format(constant.ERR_ITEM_NOT_FOUND, constant.LAB_INVENTORY, itemId)));

        proceedStock(inventory, reqQty);
    }
}
