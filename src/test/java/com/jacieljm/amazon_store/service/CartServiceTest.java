package com.jacieljm.amazon_store.service;

import com.jacieljm.amazon_store.model.Cart;
import com.jacieljm.amazon_store.model.Product;
import com.jacieljm.amazon_store.repository.CartRepository;
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

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private String sessionId;
    private Long cartId;
    private Product product;
    private Long productId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sessionId = "session1";
        cartId = 1L;
        cart = new Cart();
        cart.setSessionId(sessionId);
        cart.setId(cartId);
        product = new Product();
        productId = 1L;
        product.setId(productId);
    }

    @Test
    @DisplayName("Cart is created successfully")
    void shouldCreateCartSuccessfully() {
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.createCart(sessionId);
        assertNotNull(result);
        assertEquals(sessionId, result.getSessionId());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Cart is retrieved by id successfully")
    void shouldReturnCartByIdIfExists() {
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartService.getCartById(cartId);
        assertTrue(result.isPresent());
        assertEquals(cartId, result.get().getId());
    }

    @Test
    @DisplayName("Not found is returned when cart does not exist")
    void shouldReturnNotFoundIfCartByIdDoesNotExist() {
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        Optional<Cart> result = cartService.getCartById(cartId);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Cart is retrieved by session id successfully")
    void shouldReturnCartBySessionIdIfExists() {
        when(cartRepository.findBySessionId(sessionId)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartService.getCartBySessionId(sessionId);
        assertTrue(result.isPresent());
        assertEquals(sessionId, result.get().getSessionId());
    }

    @Test
    @DisplayName("Not found is returned when cart by session id does not exist")
    void shouldReturnNotFoundIfCartBySessionIdDoesNotExist() {
        when(cartRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

        Optional<Cart> result = cartService.getCartBySessionId(sessionId);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Product is added to cart successfully")
    void shouldAddProductToCartSuccessfully() {
        when(cartRepository.findBySessionId(sessionId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addProductToCart(sessionId, productId);
        assertNotNull(result);
        assertTrue(result.getProducts().contains(product));
        verify(cartRepository).findBySessionId(sessionId);
        verify(productRepository).findById(productId);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Return null when adding product to non-existent cart")
    void shouldReturnNullWhenAddingProductToNonExistentCart() {
        when(cartRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

        Cart result = cartService.addProductToCart(sessionId, productId);
        assertNull(result);
        verify(cartRepository).findBySessionId(sessionId);
    }

    @Test
    @DisplayName("Return null when adding non-existent product to cart")
    void shouldReturnNullWhenAddingNonExistentProductToCart() {
        when(cartRepository.findBySessionId(sessionId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Cart result = cartService.addProductToCart(sessionId, productId);
        assertNull(result);
        verify(cartRepository).findBySessionId(sessionId);
        verify(productRepository).findById(productId);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    @DisplayName("Product is removed from cart successfully")
    void shouldRemoveProductFromCartSuccessfully() {
        cart.getProducts().add(product);
        when(cartRepository.findBySessionId(sessionId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.removeProductFromCart(sessionId, productId);
        assertNotNull(result);
        assertFalse(result.getProducts().contains(product));
        verify(cartRepository).findBySessionId(sessionId);
        verify(productRepository).findById(productId);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Return null when removing product from non-existent cart")
    void shouldReturnNullWhenRemovingProductFromNonExistentCart() {
        when(cartRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

        Cart result = cartService.removeProductFromCart(sessionId, productId);
        assertNull(result);
        verify(cartRepository).findBySessionId(sessionId);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    @DisplayName("Return null when removing non-existent product from cart")
    void shouldReturnNullWhenRemovingNonExistentProductFromCart() {
        when(cartRepository.findBySessionId(sessionId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Cart result = cartService.removeProductFromCart(sessionId, productId);
        assertNull(result);
        verify(cartRepository).findBySessionId(sessionId);
        verify(productRepository).findById(productId);
        verify(cartRepository, never()).save(any(Cart.class));
    }
}