package com.cafeteria.productos.mappers;

import com.cafeteria.productos.dto.response.ProductsResponseDTO;
import com.cafeteria.productos.entity.Products;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListProductsMapper extends Converter<List<Products>, List<ProductsResponseDTO>> {

    @Override
    List<ProductsResponseDTO> convert(List<Products> source);
}
