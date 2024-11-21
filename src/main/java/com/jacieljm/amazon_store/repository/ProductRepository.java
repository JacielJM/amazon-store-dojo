package com.jacieljm.amazon_store.repository;

import com.jacieljm.amazon_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}