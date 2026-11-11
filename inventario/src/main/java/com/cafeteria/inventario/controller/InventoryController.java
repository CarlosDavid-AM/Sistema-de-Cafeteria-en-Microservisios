package com.cafeteria.inventario.controller;

import com.cafeteria.inventario.dto.response.ProductDTO;
import com.cafeteria.inventario.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/product/{productId}")
    public ProductDTO getProductById(@PathVariable Long productId) {
        return inventoryService.getProductById(productId);
    }
}
