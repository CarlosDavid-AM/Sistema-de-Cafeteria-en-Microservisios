package com.cafeteria.inventario.mappers;

import com.cafeteria.inventario.dto.response.InventoryResponseDTO;
import com.cafeteria.inventario.entity.Inventory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListInventoryMapperImpl implements ListInventoryMapper {

    private final ConversionService conversionService;

    public ListInventoryMapperImpl(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public List<InventoryResponseDTO> convert(List<Inventory> source) {
        return source.stream()
                .map(inventory -> conversionService.convert(inventory, InventoryResponseDTO.class))
                .toList();
    }
}
