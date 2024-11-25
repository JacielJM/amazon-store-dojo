package com.jacieljm.amazon_store.controller;

import com.jacieljm.amazon_store.model.Product;
import com.jacieljm.amazon_store.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    private Long productId;
    private int quantity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productId = 1L;
        quantity = 5;
        Product product = new Product();
        product.setId(productId);
    }

    @Test
    @DisplayName("Stock is updated successfully")
    void shouldUpdateStockSuccessfully() {
        when(stockService.updateStock(productId, quantity)).thenReturn(true);

        ResponseEntity<Void> response = stockController.updateStock(productId, quantity);
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    @DisplayName("Stock update fails if product does not exist")
    void shouldReturnNotFoundIfProductDoesNotExist() {
        when(stockService.updateStock(productId, quantity)).thenReturn(false);

        ResponseEntity<Void> response = stockController.updateStock(productId, quantity);
        assertEquals(ResponseEntity.notFound().build(), response);
    }
}