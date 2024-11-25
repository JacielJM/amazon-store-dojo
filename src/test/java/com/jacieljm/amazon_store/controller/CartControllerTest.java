package com.jacieljm.amazon_store.controller;

import com.jacieljm.amazon_store.model.Cart;
import com.jacieljm.amazon_store.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private Cart cart;
    private String sessionId;
    private Long productId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart();
        sessionId = "session1";
        cart.setSessionId(sessionId);
        productId = 1L;
    }

    @Test
    @DisplayName("Cart is created successfully")
    void shouldCreateCartSuccessfully() {
        when(cartService.createCart(sessionId)).thenReturn(cart);

        Cart result = cartController.createCart(sessionId);
        assertNotNull(result);
        assertEquals(sessionId, result.getSessionId());
    }

    @Test
    @DisplayName("Cart is retrieved by id successfully")
    void shouldReturnCartByIdIfExists() {
        Long id = 1L;
        cart.setId(id);

        when(cartService.getCartById(id)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> response = cartController.getCartById(id);
        assertEquals(ResponseEntity.ok(cart), response);
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @DisplayName("Not found is returned when cart does not exist")
    void shouldReturnNotFoundIfCartByIdDoesNotExist() {
        Long id = 1L;

        when(cartService.getCartById(id)).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.getCartById(id);
        assertEquals(ResponseEntity.notFound().build(), response);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Cart is retrieved by session id successfully")
    void shouldReturnCartBySessionIdIfExists() {

        when(cartService.getCartBySessionId(sessionId)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> response = cartController.getCartBySessionId(sessionId);
        assertEquals(ResponseEntity.ok(cart), response);
        assertEquals(sessionId, response.getBody().getSessionId());
    }

    @Test
    @DisplayName("Not found is returned when cart by session id does not exist")
    void shouldReturnNotFoundIfCartBySessionIdDoesNotExist() {

        when(cartService.getCartBySessionId(sessionId)).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.getCartBySessionId(sessionId);
        assertEquals(ResponseEntity.notFound().build(), response);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Product is added to cart successfully")
    void shouldAddProductToCartSuccessfully() {

        when(cartService.addProductToCart(sessionId, productId)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.addProductToCart(sessionId, productId);
        assertEquals(ResponseEntity.ok(cart), response);
    }

    @Test
    @DisplayName("Not found is returned when adding non-existent product to cart")
    void shouldReturnNotFoundWhenAddingNonExistentProductToCart() {

        when(cartService.addProductToCart(sessionId, productId)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.addProductToCart(sessionId, productId);
        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    @DisplayName("Not found is returned when adding product to non-existent cart")
    void shouldReturnNotFoundWhenAddingProductToNonExistentCart() {

        when(cartService.addProductToCart(sessionId, productId)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.addProductToCart(sessionId, productId);
        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    @DisplayName("Product is removed from cart successfully")
    void shouldRemoveProductFromCartSuccessfully() {

        when(cartService.removeProductFromCart(sessionId, productId)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.removeProductFromCart(sessionId, productId);
        assertEquals(ResponseEntity.ok(cart), response);
    }

    @Test
    @DisplayName("Not found is returned when removing product from non-existent cart")
    void shouldReturnNotFoundWhenRemovingProductFromNonExistentCart() {

        when(cartService.removeProductFromCart(sessionId, productId)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.removeProductFromCart(sessionId, productId);
        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    @DisplayName("Not found is returned when removing non-existent product from cart")
    void shouldReturnNotFoundWhenRemovingNonExistentProductFromCart() {

        when(cartService.removeProductFromCart(sessionId, productId)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.removeProductFromCart(sessionId, productId);
        assertEquals(ResponseEntity.notFound().build(), response);
    }
}