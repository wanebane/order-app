package com.rivaldy.orderapp.service;

import com.rivaldy.orderapp.exception.DataNotFoundException;
import com.rivaldy.orderapp.model.entity.Item;
import com.rivaldy.orderapp.model.request.ItemPaginationRequest;
import com.rivaldy.orderapp.model.request.ItemAddRequest;
import com.rivaldy.orderapp.model.request.ItemUpdateRequest;
import com.rivaldy.orderapp.model.response.ItemResponse;
import com.rivaldy.orderapp.model.response.PageResponse;
import com.rivaldy.orderapp.repository.ItemRepository;
import com.rivaldy.orderapp.util.constant.AppConstant;
import com.rivaldy.orderapp.util.specification.EntitySpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final EntitySpecification entitySpecification = new EntitySpecification();
    private final AppConstant constant = new AppConstant();

    public ItemResponse toResponse(Item item){
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }

    public ItemResponse toResponseId(Item item){
        ItemResponse response = toResponse(item);
        response.setId(item.getId());
        return response;
    }

    public Item findItem(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format(constant.ERR_NOT_FOUND, constant.LAB_ITEM, id)));
    }

    public PageResponse<ItemResponse> getPage(ItemPaginationRequest request){
        Specification<Item> spec = entitySpecification.filter(request);
        Page<Item> page = itemRepository.findAll(spec, request.toPageable());

        return PageResponse.<ItemResponse>builder()
                .content(page.getContent().stream()
                        .map(this::toResponseId)
                        .collect(Collectors.toList()))
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .pageSize(page.getSize())
                .build();
    }

    public ItemResponse getDetail(Long id){
        return toResponse(findItem(id));
    }

    public ItemResponse addItem(ItemAddRequest request){
        Item item = Item.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        itemRepository.save(item);
        return toResponseId(item);
    }

    public ItemResponse updateItem(Long id, ItemUpdateRequest request){
        Item item = findItem(id);
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        itemRepository.save(item);
        return toResponse(item);
    }

    public void deleteItem(Long id){
        if (!itemRepository.existsById(id)){
            throw new DataNotFoundException(String.format(constant.ERR_NOT_FOUND, constant.LAB_ITEM, id));
        }
        itemRepository.deleteById(id);
    }
}
