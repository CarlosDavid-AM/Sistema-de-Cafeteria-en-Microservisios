package com.cafeteria.inventario.service;

import com.cafeteria.inventario.conector.ProductConnector;
import com.cafeteria.inventario.dto.request.InventoryRequestDTO;
import com.cafeteria.inventario.dto.response.InventoryResponseDTO;
import com.cafeteria.inventario.dto.response.ProductDTO;
import com.cafeteria.inventario.entity.Inventory;
import com.cafeteria.inventario.repository.InventoryRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public InventoryResponseDTO saveInventory(InventoryRequestDTO inventoryResponseDTO) {
        ProductDTO productDTO = getProductById(inventoryResponseDTO.getProductId());

        if (productDTO == null) {
            throw new RuntimeException("Product not found with ID: " + inventoryResponseDTO.getProductId());
        }

        Inventory transformed = conversionService.convert(inventoryResponseDTO, Inventory.class);
        Inventory savedInventory = inventoryRepository.save(Objects.requireNonNull(transformed));

        InventoryResponseDTO responseDTO = conversionService.convert(savedInventory, InventoryResponseDTO.class);

        responseDTO.setProductName(productDTO.getProduct_name());
        responseDTO.setProductCategory(productDTO.getCategory());

        return responseDTO;
    }
}
