package com.cafeteria.inventario.service;

import com.cafeteria.inventario.conector.ProductConnector;
import com.cafeteria.inventario.dto.request.InventoryRequestDTO;
import com.cafeteria.inventario.dto.response.InventoryResponseDTO;
import com.cafeteria.inventario.dto.response.ProductDTO;
import com.cafeteria.inventario.entity.Inventory;
import com.cafeteria.inventario.enums.APIError;
import com.cafeteria.inventario.exceptions.InventoryExceptions;
import com.cafeteria.inventario.mappers.ListInventoryMapper;
import com.cafeteria.inventario.repository.InventoryRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InventoryService {

    private ConversionService conversionService;
    private InventoryRepository inventoryRepository;
    private ProductConnector productConnector;
    private ListInventoryMapper listInventoryMapper;

    public InventoryService(ConversionService conversionService, InventoryRepository inventoryRepository,
                            ProductConnector productConnector, ListInventoryMapper listInventoryMapper) {
        this.conversionService = conversionService;
        this.inventoryRepository = inventoryRepository;
        this.productConnector = productConnector;
        this.listInventoryMapper = listInventoryMapper;
    }

    private ProductDTO getProductById(Long productId) {
        return productConnector.getProductById(productId);
    }

    public InventoryResponseDTO saveInventory(InventoryRequestDTO inventoryResponseDTO) {
        ProductDTO productDTO = validateAndGetProduct(inventoryResponseDTO.getProductId());

        Inventory transformed = conversionService.convert(inventoryResponseDTO, Inventory.class);
        Inventory savedInventory = inventoryRepository.save(Objects.requireNonNull(transformed));

        InventoryResponseDTO responseDTO = conversionService.convert(savedInventory, InventoryResponseDTO.class);

        enrichWithProductData(responseDTO, productDTO);

        return responseDTO;
    }

    public List<InventoryResponseDTO> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return listInventoryMapper.convert(inventories);
    }

    public InventoryResponseDTO getInventoryById(Long inventoryId) {
        Inventory inventory = findInventoryOrThrow(inventoryId);

        InventoryResponseDTO responseDTO = conversionService.convert(inventory, InventoryResponseDTO.class);

        ProductDTO productDTO = validateAndGetProduct(inventory.getProductId());
        enrichWithProductData(responseDTO, productDTO);

        return responseDTO;
    }

    public InventoryResponseDTO updateInventory(Long inventoryId, InventoryRequestDTO inventoryRequestDTO) {
        findInventoryOrThrow(inventoryId);

        ProductDTO productDTO = validateAndGetProduct(inventoryRequestDTO.getProductId());

        Inventory updatedInventory = conversionService.convert(inventoryRequestDTO, Inventory.class);
        Objects.requireNonNull(updatedInventory).setInventoryId(inventoryId);
        Inventory savedInventory = inventoryRepository.save(updatedInventory);

        InventoryResponseDTO responseDTO = conversionService.convert(savedInventory, InventoryResponseDTO.class);

        enrichWithProductData(responseDTO, productDTO);

        return responseDTO;
    }

    public void deleteInventory(Long inventoryId) {
        findInventoryOrThrow(inventoryId);
        inventoryRepository.deleteById(inventoryId);
    }

    // ── Métodos privados reutilizables ────────────────────────────────────────

    private ProductDTO validateAndGetProduct(Long productId) {
        ProductDTO productDTO = getProductById(productId);
        if (productDTO == null) {
            throw new InventoryExceptions(APIError.RESOURCE_NOT_FOUND);
        }
        return productDTO;
    }

    private void enrichWithProductData(InventoryResponseDTO responseDTO, ProductDTO productDTO) {
        responseDTO.setProductName(productDTO.getProduct_name());
        responseDTO.setProductCategory(productDTO.getCategory());
    }

    private Inventory findInventoryOrThrow(Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryExceptions(APIError.RESOURCE_NOT_FOUND));
    }
}
