package com.cafeteria.inventario.service;

import com.cafeteria.inventario.repository.InventoryRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private ConversionService conversionService;
    private InventoryRepository inventoryRepository;

    public InventoryService(ConversionService conversionService, InventoryRepository inventoryRepository) {
        this.conversionService = conversionService;
        this.inventoryRepository = inventoryRepository;
    }
}
