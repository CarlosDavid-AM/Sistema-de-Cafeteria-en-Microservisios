package com.cafeteria.productos.controller;

import com.cafeteria.productos.dto.request.ProductsRequestDTO;
import com.cafeteria.productos.dto.response.ProductsPageResponseDTO;
import com.cafeteria.productos.dto.response.ProductsResponseDTO;
import com.cafeteria.productos.enums.CategoryEnum;
import com.cafeteria.productos.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<ProductsPageResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int pageActual,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(productsService.findAll(pageActual, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductsResponseDTO> create(@RequestBody ProductsRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody ProductsRequestDTO requestDTO) {
        return ResponseEntity.ok(productsService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<ProductsResponseDTO> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.toggleActive(id));
    }

    @GetMapping("/filter/category")
    public ResponseEntity<ProductsPageResponseDTO> findByCategory(
            @RequestParam CategoryEnum category,
            @RequestParam(defaultValue = "0") int pageActual,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(productsService.findByCategory(category, pageActual, pageSize));
    }

    @GetMapping("/filter/name")
    public ResponseEntity<ProductsPageResponseDTO> findByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int pageActual,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(productsService.findByName(name, pageActual, pageSize));
    }
}
