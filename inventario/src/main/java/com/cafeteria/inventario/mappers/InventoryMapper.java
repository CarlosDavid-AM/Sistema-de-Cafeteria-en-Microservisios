package com.cafeteria.inventario.mappers;

import com.cafeteria.inventario.dto.response.InventoryResponseDTO;
import com.cafeteria.inventario.entity.Inventory;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface InventoryMapper extends Converter<Inventory, InventoryResponseDTO> {

    @Override
    InventoryResponseDTO convert(Inventory source);

}
