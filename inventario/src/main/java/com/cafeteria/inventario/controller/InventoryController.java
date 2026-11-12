package com.cafeteria.inventario.controller;

import com.cafeteria.inventario.dto.request.InventoryRequestDTO;
import com.cafeteria.inventario.dto.response.InventoryResponseDTO;
import com.cafeteria.inventario.dto.response.ProductDTO;
import com.cafeteria.inventario.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> saveInventory(@RequestBody InventoryRequestDTO inventoryRequestDTO) {
        InventoryResponseDTO savedInventory = inventoryService.saveInventory(inventoryRequestDTO);
        return ResponseEntity.ok(savedInventory);
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventories() {
        List<InventoryResponseDTO> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseDTO> getInventoryById(@PathVariable Long inventoryId) {
        InventoryResponseDTO inventory = inventoryService.getInventoryById(inventoryId);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(@PathVariable Long inventoryId,
                                                                @RequestBody InventoryRequestDTO inventoryRequestDTO) {
        InventoryResponseDTO updatedInventory = inventoryService.updateInventory(inventoryId, inventoryRequestDTO);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
