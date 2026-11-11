package com.cafeteria.productos.dto.response;

import com.cafeteria.productos.enums.CategoryEnum;

public class ProductsResponseDTO {

    private Long product_id;
    private String product_name;
    private CategoryEnum category;
    private float price;
    private boolean product_active;

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isProduct_active() {
        return product_active;
    }

    public void setProduct_active(boolean product_active) {
        this.product_active = product_active;
    }
}
