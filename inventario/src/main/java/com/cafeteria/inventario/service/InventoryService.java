package com.cafeteria.inventario.service;

import com.cafeteria.inventario.conector.ProductConnector;
import com.cafeteria.inventario.dto.response.ProductDTO;
import com.cafeteria.inventario.repository.InventoryRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private ConversionService conversionService;
    private InventoryRepository inventoryRepository;
    private ProductConnector productConnector;

    public InventoryService(ConversionService conversionService, InventoryRepository inventoryRepository, ProductConnector productConnector) {
        this.conversionService = conversionService;
        this.inventoryRepository = inventoryRepository;
        this.productConnector = productConnector;
    }

    public ProductDTO getProductById(Long productId) {
        return productConnector.getProductById(productId);
    }
}
