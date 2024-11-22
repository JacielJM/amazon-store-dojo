package com.jacieljm.amazon_store.service;

import com.jacieljm.amazon_store.model.Cart;
import com.jacieljm.amazon_store.model.Product;
import com.jacieljm.amazon_store.repository.CartRepository;
import com.jacieljm.amazon_store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }
    public Cart createCart(String sessionId) {
        Cart cart = new Cart();
        cart.setSessionId(sessionId);
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Optional<Cart> getCartBySessionId(String sessionId) {
        return cartRepository.findBySessionId(sessionId);
    }

    public Cart addProductToCart(String sessionId, Long productId) {
        Optional<Cart> cart = cartRepository.findBySessionId(sessionId);
        Optional<Product> product = productRepository.findById(productId);
        if (cart.isPresent() && product.isPresent()) {
            cart.get().getProducts().add(product.get());
            return cartRepository.save(cart.get());
        }
        return null;
    }

    public Cart removeProductFromCart(String sessionId, Long productId) {
        Optional<Cart> cart = cartRepository.findBySessionId(sessionId);
        Optional<Product> product = productRepository.findById(productId);
        if (cart.isPresent() && product.isPresent()) {
            cart.get().getProducts().remove(product.get());
            return cartRepository.save(cart.get());
        }
        return null;
    }
}