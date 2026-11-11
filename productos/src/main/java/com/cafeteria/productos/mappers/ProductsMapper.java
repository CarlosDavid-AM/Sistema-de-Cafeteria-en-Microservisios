package com.cafeteria.productos.mappers;

import com.cafeteria.productos.dto.response.ProductsResponseDTO;
import com.cafeteria.productos.entity.Products;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ProductsMapper extends Converter<Products, ProductsResponseDTO> {

    @Override
    ProductsResponseDTO convert(Products source);
}
