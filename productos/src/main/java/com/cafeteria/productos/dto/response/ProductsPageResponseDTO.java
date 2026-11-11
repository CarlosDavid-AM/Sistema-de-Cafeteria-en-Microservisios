package com.cafeteria.productos.dto.response;

import java.util.List;

public class ProductsPageResponseDTO {

    private int pageActual;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private List<ProductsResponseDTO> products;

    public int getPageActual() {
        return pageActual;
    }

    public void setPageActual(int pageActual) {
        this.pageActual = pageActual;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ProductsResponseDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsResponseDTO> products) {
        this.products = products;
    }
}
