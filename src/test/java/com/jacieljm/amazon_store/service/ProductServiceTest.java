package com.jacieljm.amazon_store.service;

import com.jacieljm.amazon_store.model.Product;
import com.jacieljm.amazon_store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Long productId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productId = 1L;
        product = new Product();
        product.setId(productId);
    }

    @Test
    @DisplayName("Product is retrieved by id successfully")
    void shouldReturnProductByIdIfExists() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);
        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
    }

    @Test
    @DisplayName("Not found is returned when product does not exist")
    void shouldReturnNotFoundIfProductByIdDoesNotExist() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(productId);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("All products are retrieved successfully")
    void shouldReturnAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("Product is saved successfully")
    void shouldSaveProductSuccessfully() {
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveProduct(product);
        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Product is deleted successfully")
    void shouldDeleteProductSuccessfully() {
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);
        verify(productRepository).deleteById(productId);
    }
}