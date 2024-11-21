package com.jacieljm.amazon_store.service;

import com.jacieljm.amazon_store.model.Product;
import com.jacieljm.amazon_store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {
    private final ProductRepository productRepository;

    public StockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean updateStock(Long productId, int quantity) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent() && product.get().getStock() >= quantity) {
            product.get().setStock(product.get().getStock() - quantity);
            productRepository.save(product.get());
            return true;
        }
        return false;
    }
}