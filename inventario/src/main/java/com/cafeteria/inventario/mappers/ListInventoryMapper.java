package com.cafeteria.inventario.mappers;

import com.cafeteria.inventario.dto.response.InventoryResponseDTO;
import com.cafeteria.inventario.entity.Inventory;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public interface ListInventoryMapper extends Converter<List<Inventory>, List<InventoryResponseDTO>> {

    @Override
    List<InventoryResponseDTO> convert(List<Inventory> source);

}
