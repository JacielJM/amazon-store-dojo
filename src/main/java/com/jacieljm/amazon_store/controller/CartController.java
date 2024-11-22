package com.jacieljm.amazon_store.controller;

import com.jacieljm.amazon_store.model.Cart;
import com.jacieljm.amazon_store.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart createCart(@RequestParam String sessionId) {
        return cartService.createCart(sessionId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = cartService.getCartById(id);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get")
    public ResponseEntity<Cart> getCartBySessionId(@RequestParam String sessionId) {
        Optional<Cart> cart = cartService.getCartBySessionId(sessionId);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{sessionId}/products/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable String sessionId, @PathVariable Long productId) {
        Cart cart = cartService.addProductToCart(sessionId, productId);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{sessionId}/products/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable String sessionId, @PathVariable Long productId) {
        Cart cart = cartService.removeProductFromCart(sessionId, productId);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}