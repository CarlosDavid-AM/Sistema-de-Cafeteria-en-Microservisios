package com.cafeteria.ventas.mappers;

import com.cafeteria.ventas.dto.request.VentasRequestDTO;
import com.cafeteria.ventas.entity.Venta;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface VentasDtoMapper extends Converter<VentasRequestDTO, Venta> {

    @Override
    Venta convert(VentasRequestDTO source);
}
