package com.cafeteria.inventario.controller;

import com.cafeteria.inventario.dto.request.InventoryRequestDTO;
import com.cafeteria.inventario.dto.response.InventoryResponseDTO;
import com.cafeteria.inventario.dto.response.ProductDTO;
import com.cafeteria.inventario.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> saveInventory(@RequestBody InventoryRequestDTO inventoryRequestDTO) {
        InventoryResponseDTO savedInventory = inventoryService.saveInventory(inventoryRequestDTO);
        return ResponseEntity.ok(savedInventory);
    }
}
