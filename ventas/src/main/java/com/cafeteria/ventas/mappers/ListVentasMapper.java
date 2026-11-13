package com.cafeteria.ventas.mappers;

import com.cafeteria.ventas.dto.response.VentasResponseDTO;
import com.cafeteria.ventas.entity.Venta;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VentasMapper.class})
public interface ListVentasMapper extends Converter<List<Venta>, List<VentasResponseDTO>> {

    @Override
    List<VentasResponseDTO> convert(List<Venta> source);
}
