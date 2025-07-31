package com.rivaldy.orderapp.service;

import com.rivaldy.orderapp.exception.DataNotFoundException;
import com.rivaldy.orderapp.model.entity.Item;
import com.rivaldy.orderapp.model.request.ItemAddRequest;
import com.rivaldy.orderapp.model.request.ItemPaginationRequest;
import com.rivaldy.orderapp.model.request.ItemUpdateRequest;
import com.rivaldy.orderapp.model.response.ItemResponse;
import com.rivaldy.orderapp.model.response.PageResponse;
import com.rivaldy.orderapp.repository.ItemRepository;
import com.rivaldy.orderapp.util.specification.EntitySpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private EntitySpecification entitySpecification;

    @Spy
    @InjectMocks
    private ItemService itemService;

    private Item item;
    private ItemAddRequest addRequest;
    private ItemUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id(1L)
                .name("Test Item")
                .price(BigDecimal.valueOf(100.50))
                .build();

        addRequest = new ItemAddRequest();
        addRequest.setName("New Item");
        addRequest.setPrice(BigDecimal.valueOf(200.0));

        updateRequest = new ItemUpdateRequest();
        updateRequest.setName("Updated Item");
        updateRequest.setPrice(BigDecimal.valueOf(300.0));
    }

    @Test
    void findItem_WhenExists_ShouldReturnItem() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemService.findItem(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getName());
    }

    @Test
    void findItem_WhenNotExists_ShouldThrowException() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> itemService.findItem(1L));

        assertEquals("Data item with id 1 is not found", exception.getMessage());
    }

    @Test
    void getPage_ShouldReturnPageResponse() {
        ItemPaginationRequest request = new ItemPaginationRequest();
        Page<Item> page = new PageImpl<>(List.of(item));

        when(itemRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        PageResponse<ItemResponse> result = itemService.getPage(request);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getCurrentPage());
    }

    @Test
    void getDetail_ShouldReturnItemResponse() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ItemResponse result = itemService.getDetail(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getName());
    }

    @Test
    void addItem_ShouldSaveAndReturnResponse() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemResponse result = itemService.addItem(addRequest);

        assertNotNull(result);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void updateItem_ShouldUpdateAndReturnResponse() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemResponse result = itemService.updateItem(1L, updateRequest);

        assertNotNull(result);
        assertEquals("Updated Item", item.getName());
        assertEquals(BigDecimal.valueOf(300.0), item.getPrice());
        verify(itemRepository).save(item);
    }

    @Test
    void deleteItem_WhenExists_ShouldDelete() {
        when(itemRepository.existsById(1L)).thenReturn(true);
        doNothing().when(itemRepository).deleteById(1L);

        assertDoesNotThrow(() -> itemService.deleteItem(1L));
        verify(itemRepository).deleteById(1L);
    }

    @Test
    void deleteItem_WhenNotExists_ShouldThrowException() {
        when(itemRepository.existsById(1L)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> itemService.deleteItem(1L));

        assertEquals("Data item with id 1 is not found", exception.getMessage());
        verify(itemRepository, never()).deleteById(any());
    }
}