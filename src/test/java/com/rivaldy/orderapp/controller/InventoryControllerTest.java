package com.rivaldy.orderapp.controller;

import com.rivaldy.orderapp.model.request.InventoryAddRequest;
import com.rivaldy.orderapp.model.request.InventoryPaginationRequest;
import com.rivaldy.orderapp.model.request.InventoryUpdateRequest;
import com.rivaldy.orderapp.model.response.GeneralResponse;
import com.rivaldy.orderapp.model.response.InventoryResponse;
import com.rivaldy.orderapp.model.response.PageResponse;
import com.rivaldy.orderapp.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @Spy
    @InjectMocks
    private InventoryController inventoryController;

    private InventoryResponse inventoryResponse;
    private PageResponse<InventoryResponse> pageResponse;

    @BeforeEach
    void setUp() {
        inventoryResponse = InventoryResponse.builder()
                .id(1L)
                .qty(10)
                .type("IN")
                .build();

        pageResponse = PageResponse.<InventoryResponse>builder()
                .content(List.of(inventoryResponse))
                .currentPage(0)
                .totalPages(1)
                .totalItems(1)
                .pageSize(10)
                .build();
    }

    @Test
    void page_ShouldReturnOkResponse() {
        InventoryPaginationRequest request = new InventoryPaginationRequest();
        when(inventoryService.getPage(request)).thenReturn(pageResponse);

        ResponseEntity<GeneralResponse> response = inventoryController.page(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to fetch page data of inventory", response.getBody().getMessage());
        assertEquals(pageResponse, response.getBody().getData());
    }

    @Test
    void detail_ShouldReturnOkResponse() {
        when(inventoryService.getDetail(1L)).thenReturn(inventoryResponse);

        ResponseEntity<GeneralResponse> response = inventoryController.detail(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to get detail inventory with id 1", response.getBody().getMessage());
        assertEquals(inventoryResponse, response.getBody().getData());
    }

    @Test
    void add_ShouldReturnCreatedResponse() {
        InventoryAddRequest request = new InventoryAddRequest();
        request.setItemId(1L);
        request.setQty(10);
        request.setType("IN");

        when(inventoryService.addInventory(request)).thenReturn(inventoryResponse);

        ResponseEntity<GeneralResponse> response = inventoryController.add(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to add inventory data", response.getBody().getMessage());
        assertEquals(inventoryResponse, response.getBody().getData());
    }

    @Test
    void update_ShouldReturnOkResponse() {
        InventoryUpdateRequest request = new InventoryUpdateRequest();
        request.setItemId(1L);
        request.setQty(20);
        request.setType("OUT");

        when(inventoryService.updateInventory(1L, request)).thenReturn(inventoryResponse);

        ResponseEntity<GeneralResponse> response = inventoryController.update(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to update inventory with id 1", response.getBody().getMessage());
        assertEquals(inventoryResponse, response.getBody().getData());
    }

    @Test
    void delete_ShouldReturnOkResponse() {
        doNothing().when(inventoryService).deleteInventory(1L);

        ResponseEntity<GeneralResponse> response = inventoryController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success to delete inventory with id 1", response.getBody().getMessage());
        assertNull(response.getBody().getData());
        verify(inventoryService).deleteInventory(1L);
    }
}