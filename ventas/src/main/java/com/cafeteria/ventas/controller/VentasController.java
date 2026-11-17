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
        return ResponseEntity.ok(ventasService.getAllVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasResponseDTO> getVentasById(@PathVariable Long id) {
        VentasResponseDTO venta = ventasService.getVentasById(id);
        return new ResponseEntity<>(venta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VentasResponseDTO> createVentas(@RequestBody VentasRequestDTO ventasRequestDTO) {
        VentasResponseDTO createdVenta = ventasService.createVentas(ventasRequestDTO);
        return new ResponseEntity<>(createdVenta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasResponseDTO> updateVenta(@PathVariable Long id, @RequestBody VentasRequestDTO requestDTO) {
        VentasResponseDTO response = ventasService.updateVenta(id, requestDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ventasService.deleteVenta(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
