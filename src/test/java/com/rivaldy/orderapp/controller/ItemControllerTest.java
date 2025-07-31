package com.rivaldy.orderapp.controller;

import com.rivaldy.orderapp.model.request.ItemAddRequest;
import com.rivaldy.orderapp.model.request.ItemPaginationRequest;
import com.rivaldy.orderapp.model.request.ItemUpdateRequest;
import com.rivaldy.orderapp.model.response.GeneralResponse;
import com.rivaldy.orderapp.model.response.ItemResponse;
import com.rivaldy.orderapp.model.response.PageResponse;
import com.rivaldy.orderapp.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @Spy
    @InjectMocks
    private ItemController itemController;

    private ItemResponse itemResponse;
    private PageResponse<ItemResponse> pageResponse;

    @BeforeEach
    void setUp() {
        itemResponse = ItemResponse.builder()
                .id(1L)
                .name("Test Item")
                .price(BigDecimal.valueOf(100.50))
                .build();

        pageResponse = PageResponse.<ItemResponse>builder()
                .content(List.of(itemResponse))
                .currentPage(0)
                .totalPages(1)
                .totalItems(1)
                .pageSize(10)
                .build();
    }

    @Test
    void page_ShouldReturnOkResponse() {
        ItemPaginationRequest request = new ItemPaginationRequest();
        when(itemService.getPage(request)).thenReturn(pageResponse);

        ResponseEntity<GeneralResponse> response = itemController.page(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to fetch page data of item", response.getBody().getMessage());
        assertEquals(pageResponse, response.getBody().getData());
    }

    @Test
    void detail_ShouldReturnOkResponse() {
        when(itemService.getDetail(1L)).thenReturn(itemResponse);

        ResponseEntity<GeneralResponse> response = itemController.detail(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to get detail item with id 1", response.getBody().getMessage());
        assertEquals(itemResponse, response.getBody().getData());
    }

    @Test
    void add_ShouldReturnCreatedResponse() {
        ItemAddRequest request = new ItemAddRequest();
        request.setName("New Item");
        request.setPrice(BigDecimal.valueOf(200.0));

        when(itemService.addItem(request)).thenReturn(itemResponse);

        ResponseEntity<GeneralResponse> response = itemController.add(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to add item data", response.getBody().getMessage());
        assertEquals(itemResponse, response.getBody().getData());
    }

    @Test
    void update_ShouldReturnOkResponse() {
        ItemUpdateRequest request = new ItemUpdateRequest();
        request.setName("Updated Item");
        request.setPrice(BigDecimal.valueOf(300.0));

        when(itemService.updateItem(1L, request)).thenReturn(itemResponse);

        ResponseEntity<GeneralResponse> response = itemController.update(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to update item with id 1", response.getBody().getMessage());
        assertEquals(itemResponse, response.getBody().getData());
    }

    @Test
    void delete_ShouldReturnOkResponse() {
        doNothing().when(itemService).deleteItem(1L);

        ResponseEntity<GeneralResponse> response = itemController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to delete item with id 1", response.getBody().getMessage());
        assertNull(response.getBody().getData());
        verify(itemService).deleteItem(1L);
    }
}