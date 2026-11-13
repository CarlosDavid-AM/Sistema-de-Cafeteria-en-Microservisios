package com.cafeteria.ventas.controller;

import com.cafeteria.ventas.dto.request.VentasRequestDTO;
import com.cafeteria.ventas.dto.response.VentasResponseDTO;
import com.cafeteria.ventas.service.VentasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ventas")
public class VentasController {

    private final VentasService ventasService;

    public VentasController(VentasService ventasService) {
        this.ventasService = ventasService;
    }

    @GetMapping
    public ResponseEntity<List<VentasResponseDTO>> getAll() {
        return ResponseEntity.ok(ventasService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ventasService.getById(id));
    }

    @PostMapping
    public ResponseEntity<VentasResponseDTO> create(@RequestBody VentasRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventasService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasResponseDTO> update(@PathVariable Long id, @RequestBody VentasRequestDTO requestDTO) {
        return ResponseEntity.ok(ventasService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ventasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
