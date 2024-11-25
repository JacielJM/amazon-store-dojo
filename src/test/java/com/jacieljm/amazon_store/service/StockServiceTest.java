package com.jacieljm.amazon_store.service;

import com.jacieljm.amazon_store.model.Product;
import com.jacieljm.amazon_store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private StockService stockService;

    private Product product;
    private Long productId;
    private int quantity;
    private int initialStock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productId = 1L;
        quantity = 5;
        initialStock = 10;
        product = new Product();
        product.setId(productId);
        product.setStock(initialStock);
    }

    @Test
    @DisplayName("Stock is updated successfully")
    void shouldUpdateStockSuccessfully() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        boolean result = stockService.updateStock(productId, quantity);

        assertTrue(result);
        assertEquals(quantity, product.getStock());
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Stock is not updated if product does not exist")
    void shouldNotUpdateStockIfProductDoesNotExist() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        boolean result = stockService.updateStock(productId, quantity);

        assertFalse(result);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Stock is not updated if insufficient stock")
    void shouldNotUpdateStockIfInsufficientStock() {
        quantity = 15;

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        boolean result = stockService.updateStock(productId, quantity);

        assertFalse(result);
        assertEquals(initialStock, product.getStock());
        verify(productRepository, never()).save(product);
    }
}