package com.jacieljm.amazon_store.repository;

import com.jacieljm.amazon_store.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}