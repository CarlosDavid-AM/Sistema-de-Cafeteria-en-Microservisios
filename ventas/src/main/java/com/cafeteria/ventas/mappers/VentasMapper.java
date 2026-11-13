package com.cafeteria.ventas.mappers;

import com.cafeteria.ventas.dto.response.VentasResponseDTO;
import com.cafeteria.ventas.entity.Venta;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface VentasMapper extends Converter<Venta, VentasResponseDTO> {

    @Override
    VentasResponseDTO convert(Venta source);
}
