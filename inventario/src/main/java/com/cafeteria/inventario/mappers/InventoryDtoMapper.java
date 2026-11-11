package com.cafeteria.inventario.mappers;

import com.cafeteria.inventario.dto.request.InventoryRequestDTO;
import com.cafeteria.inventario.entity.Inventory;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface InventoryDtoMapper extends Converter<InventoryRequestDTO, Inventory> {

    @Override
    Inventory convert(InventoryRequestDTO source);
}
