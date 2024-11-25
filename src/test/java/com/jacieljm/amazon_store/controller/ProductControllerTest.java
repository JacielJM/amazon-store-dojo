package com.jacieljm.amazon_store.controller;

import com.jacieljm.amazon_store.model.Product;
import com.jacieljm.amazon_store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Long productId;
    private Product product;

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
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById(productId);
        assertEquals(ResponseEntity.ok(product), response);
    }

    @Test
    @DisplayName("Not found is returned when product does not exist")
    void shouldReturnNotFoundIfProductByIdDoesNotExist() {
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProductById(productId);
        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    @DisplayName("Product is created successfully")
    void shouldSaveProductSuccessfully() {
        when(productService.saveProduct(product)).thenReturn(product);

        Product response = productController.createProduct(product);
        assertNotNull(response);
        assertEquals(product.getId(), response.getId());
    }

    @Test
    @DisplayName("Product is updated successfully")
    void shouldUpdateProductSuccessfully() {
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated Name");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(100.0);
        updatedProduct.setStock(10);

        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        when(productService.saveProduct(any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(productId, updatedProduct);

        assertEquals(ResponseEntity.ok(updatedProduct), response);
        verify(productService).getProductById(productId);
        verify(productService).saveProduct(any(Product.class));
    }

    @Test
    @DisplayName("Not found is returned when updating non-existent product")
    void shouldReturnNotFoundWhenUpdatingNonExistentProduct() {
        ResponseEntity<Product> response = productController.updateProduct(productId, product);
        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    @DisplayName("All products are retrieved successfully")
    void shouldReturnAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(products);

        List<Product> productList = productController.getAllProducts();
        assertEquals(products, productList);
    }

    @Test
    @DisplayName("Empty list is returned when no products exist")
    void shouldReturnEmptyListWhenNoProductsExist() {
        when(productService.getAllProducts()).thenReturn(List.of());

        List<Product> response = productController.getAllProducts();
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Product is deleted successfully")
    void shouldDeleteProductSuccessfully() {
        doNothing().when(productService).deleteProduct(productId);

        ResponseEntity<Void> response = productController.deleteProduct(productId);
        assertEquals(ResponseEntity.noContent().build(), response);
    }
}