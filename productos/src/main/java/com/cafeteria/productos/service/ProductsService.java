package com.cafeteria.productos.service;

import com.cafeteria.productos.dto.request.ProductsRequestDTO;
import com.cafeteria.productos.dto.response.ProductsPageResponseDTO;
import com.cafeteria.productos.dto.response.ProductsResponseDTO;
import com.cafeteria.productos.entity.Products;
import com.cafeteria.productos.enums.APIError;
import com.cafeteria.productos.enums.CategoryEnum;
import com.cafeteria.productos.exceptions.ProductsExceptions;
import com.cafeteria.productos.mappers.ListProductsMapper;
import com.cafeteria.productos.mappers.ProductsMapper;
import com.cafeteria.productos.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private final ProductRepository productRepository;
    private final ProductsMapper productsMapper;
    private final ListProductsMapper listProductsMapper;

    public ProductsService(ProductRepository productRepository,
                           ProductsMapper productsMapper,
                           ListProductsMapper listProductsMapper) {
        this.productRepository = productRepository;
        this.productsMapper = productsMapper;
        this.listProductsMapper = listProductsMapper;
    }

    public ProductsPageResponseDTO findAll(int pageActual, int pageSize) {
        Pageable pageable = PageRequest.of(pageActual, pageSize);
        Page<Products> page = productRepository.findAll(pageable);

        List<ProductsResponseDTO> dtos = listProductsMapper.convert(page.getContent());

        ProductsPageResponseDTO response = new ProductsPageResponseDTO();
        response.setPageActual(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setProducts(dtos);

        return response;
    }

    public ProductsResponseDTO findById(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new ProductsExceptions(APIError.RESOURCE_NOT_FOUND));
        return productsMapper.convert(product);
    }

    public ProductsResponseDTO create(ProductsRequestDTO requestDTO) {
        if (requestDTO.getProduct_name() == null || requestDTO.getProduct_name().isBlank()) {
            throw new ProductsExceptions(APIError.VALIDATION_ERROR);
        }
        if (requestDTO.getCategory() == null) {
            throw new ProductsExceptions(APIError.VALIDATION_ERROR);
        }
        if (requestDTO.getPrice() <= 0) {
            throw new ProductsExceptions(APIError.VALIDATION_ERROR);
        }

        Products product = new Products();
        product.setProduct_name(requestDTO.getProduct_name());
        product.setCategory(requestDTO.getCategory());
        product.setPrice(requestDTO.getPrice());
        product.setProduct_active(requestDTO.isProduct_active());

        Products saved = productRepository.save(product);
        return productsMapper.convert(saved);
    }

    public ProductsResponseDTO update(Long id, ProductsRequestDTO requestDTO) {
        Products existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductsExceptions(APIError.RESOURCE_NOT_FOUND));

        if (requestDTO.getProduct_name() == null || requestDTO.getProduct_name().isBlank()) {
            throw new ProductsExceptions(APIError.VALIDATION_ERROR);
        }
        if (requestDTO.getCategory() == null) {
            throw new ProductsExceptions(APIError.VALIDATION_ERROR);
        }
        if (requestDTO.getPrice() <= 0) {
            throw new ProductsExceptions(APIError.VALIDATION_ERROR);
        }

        existing.setProduct_name(requestDTO.getProduct_name());
        existing.setCategory(requestDTO.getCategory());
        existing.setPrice(requestDTO.getPrice());
        existing.setProduct_active(requestDTO.isProduct_active());

        Products updated = productRepository.save(existing);
        return productsMapper.convert(updated);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductsExceptions(APIError.RESOURCE_NOT_FOUND);
        }
        productRepository.deleteById(id);
    }

    public ProductsResponseDTO toggleActive(Long id) {
        Products existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductsExceptions(APIError.RESOURCE_NOT_FOUND));

        existing.setProduct_active(!existing.isProduct_active());

        Products updated = productRepository.save(existing);
        return productsMapper.convert(updated);
    }

    public ProductsPageResponseDTO findByCategory(CategoryEnum category, int pageActual, int pageSize) {
        if (category == null) {
            throw new ProductsExceptions(APIError.VALIDATION_ERROR);
        }

        Pageable pageable = PageRequest.of(pageActual, pageSize);
        Page<Products> page = productRepository.findByCategory(category, pageable);

        List<ProductsResponseDTO> dtos = listProductsMapper.convert(page.getContent());

        ProductsPageResponseDTO response = new ProductsPageResponseDTO();
        response.setPageActual(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setProducts(dtos);

        return response;
    }

    public ProductsPageResponseDTO findByName(String name, int pageActual, int pageSize) {
        if (name == null || name.isBlank()) {
            throw new ProductsExceptions(APIError.VALIDATION_ERROR);
        }

        Pageable pageable = PageRequest.of(pageActual, pageSize);
        Page<Products> page = productRepository.searchByProductName(name.trim(), pageable);

        List<ProductsResponseDTO> dtos = listProductsMapper.convert(page.getContent());

        ProductsPageResponseDTO response = new ProductsPageResponseDTO();
        response.setPageActual(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setProducts(dtos);

        return response;
    }
}
