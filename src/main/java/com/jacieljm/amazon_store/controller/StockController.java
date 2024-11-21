package com.jacieljm.amazon_store.controller;

import com.jacieljm.amazon_store.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> updateStock(@RequestParam Long productId, @RequestParam int quantity) {
        boolean updated = stockService.updateStock(productId, quantity);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}