package com.rivaldy.orderapp.service;

import com.rivaldy.orderapp.exception.DataNotFoundException;
import com.rivaldy.orderapp.model.entity.Inventory;
import com.rivaldy.orderapp.model.entity.Item;
import com.rivaldy.orderapp.model.request.InventoryAddRequest;
import com.rivaldy.orderapp.model.request.InventoryPaginationRequest;
import com.rivaldy.orderapp.model.request.InventoryUpdateRequest;
import com.rivaldy.orderapp.model.response.InventoryResponse;
import com.rivaldy.orderapp.model.response.ItemResponse;
import com.rivaldy.orderapp.model.response.PageResponse;
import com.rivaldy.orderapp.repository.InventoryRepository;
import com.rivaldy.orderapp.repository.ItemRepository;
import com.rivaldy.orderapp.util.enumerate.InventoryType;
import com.rivaldy.orderapp.util.specification.EntitySpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private EntitySpecification entitySpecification;

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private ItemService itemService;

    private Item item;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        // Initialize test data
        item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(100.50));

        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setItem(item);
        inventory.setQty(10);
        inventory.setType(InventoryType.TOP_UP);

        InventoryAddRequest addRequest = new InventoryAddRequest();
        addRequest.setItemId(1L);
        addRequest.setQty(10);
        addRequest.setType("T");

        InventoryUpdateRequest updateRequest = new InventoryUpdateRequest();
        updateRequest.setItemId(1L);
        updateRequest.setQty(20);
        updateRequest.setType("W");

    }

    @Test
    void toResponse_ShouldConvertEntityToResponse() {
        InventoryResponse response = inventoryService.toResponse(inventory);

        assertNotNull(response);
        assertEquals(10, response.getQty());
        assertEquals("Top Up", response.getType());
    }

    @Test
    void findInventory_WhenExists_ShouldReturnInventory() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        Inventory result = inventoryService.findInventory(1L);

        assertNotNull(result);
        assertEquals(item, result.getItem());
        assertEquals("T", result.getType().getType());
    }

    @Test
    void findInventory_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(DataNotFoundException.class,
                () -> inventoryService.findInventory(1L));

        assertEquals("Data inventory with id 1 is not found", exception.getMessage());
    }

    @Test
    void getPage_ShouldReturnPageResponse() {
        InventoryPaginationRequest request = new InventoryPaginationRequest();
        Page<Inventory> page = new PageImpl<>(List.of(inventory));

        when(inventoryRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(page);

        PageResponse<InventoryResponse> result = inventoryService.getPage(request);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getCurrentPage());
        assertEquals("Top Up", result.getContent().get(0).getType());
    }

    @Test
    void getDetail_ShouldReturnInventoryResponse() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        InventoryResponse result = inventoryService.getDetail(1L);

        assertNotNull(result);
        assertEquals(10, result.getQty());
        assertEquals("Top Up", result.getType());
    }

    @Test
    void addInventory_WithValidData_ShouldSaveAndReturnResponse() {
        Item expectedItem = new Item();
        expectedItem.setId(1L);
        expectedItem.setName("Test Item");
        expectedItem.setPrice(BigDecimal.valueOf(100.50));

        InventoryAddRequest request = new InventoryAddRequest();
        request.setItemId(1L);
        request.setQty(10);
        request.setType("T");

        when(itemService.findItem(1L)).thenReturn(expectedItem);

        Inventory expectedSavedInventory = new Inventory();
        expectedSavedInventory.setId(1L);
        expectedSavedInventory.setItem(expectedItem);
        expectedSavedInventory.setQty(10);
        expectedSavedInventory.setType(InventoryType.TOP_UP);

        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(expectedSavedInventory);

        InventoryResponse expectedResponse = new InventoryResponse();
        expectedResponse.setId(1L);
        expectedResponse.setItem(new ItemResponse(1L, "Test Item", BigDecimal.valueOf(100.50)));
        expectedResponse.setQty(10);
        expectedResponse.setType("T");

        InventoryResponse actualResponse = inventoryService.addInventory(request);

        assertNotNull(actualResponse, "Response should not be null");
        assertEquals(expectedResponse.getQty(), actualResponse.getQty(),
                "Quantity should match");
        assertEquals(InventoryType.fromType(expectedResponse.getType()).getDesc(), actualResponse.getType(),
                "Type should match");

        verify(itemService).findItem(1L);
        verify(inventoryRepository).save(any(Inventory.class));
    }
    @Test
    void updateInventory_WithValidData_ShouldUpdateAndReturnResponse() {
        Inventory existingInventory = new Inventory();
        existingInventory.setId(1L);
        existingInventory.setItem(item);
        existingInventory.setQty(5); // Original quantity
        existingInventory.setType(InventoryType.TOP_UP); // Original type (TOP_UP)

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(existingInventory));

        InventoryUpdateRequest updateRequest = new InventoryUpdateRequest();
        updateRequest.setItemId(1L);
        updateRequest.setQty(20);
        updateRequest.setType("W");

        Inventory updatedInventory = new Inventory();
        updatedInventory.setId(1L);
        updatedInventory.setItem(item);
        updatedInventory.setQty(20);
        updatedInventory.setType(InventoryType.WITHDRAWAL);

        when(inventoryRepository.save(any(Inventory.class))).thenReturn(updatedInventory);

        InventoryResponse result = inventoryService.updateInventory(1L, updateRequest);

        assertNotNull(result);
        assertEquals(20, result.getQty());
        assertEquals("Withdrawal", result.getType());

        assertEquals(20, updatedInventory.getQty());
        assertEquals("Withdrawal", updatedInventory.getType().getDesc());

        verify(inventoryRepository).findById(1L);
        verify(inventoryRepository).save(any(Inventory.class));
        verify(itemRepository, never()).findById(any());
    }

    @Test
    void deleteInventory_WhenExists_ShouldDeleteSuccessfully() {
        when(inventoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(inventoryRepository).deleteById(1L);

        assertDoesNotThrow(() -> inventoryService.deleteInventory(1L));
        verify(inventoryRepository).deleteById(1L);
    }

    @Test
    void deleteInventory_WhenNotExists_ShouldThrowException() {
        when(inventoryRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(DataNotFoundException.class,
                () -> inventoryService.deleteInventory(1L));

        assertEquals("Data inventory with id 1 is not found", exception.getMessage());
        verify(inventoryRepository, never()).deleteById(any());
    }

    @Test
    void validateInventoryType_WithValidType_ShouldNotThrowException() {
        InventoryAddRequest topUpRequest = new InventoryAddRequest();
        topUpRequest.setItemId(1L);
        topUpRequest.setQty(5);
        topUpRequest.setType("T");

        InventoryAddRequest withdrawalRequest = new InventoryAddRequest();
        withdrawalRequest.setItemId(1L);
        withdrawalRequest.setQty(5);
        withdrawalRequest.setType("W");

        assertDoesNotThrow(() -> inventoryService.addInventory(topUpRequest));
        assertDoesNotThrow(() -> inventoryService.addInventory(withdrawalRequest));
    }


}