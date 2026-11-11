package com.cafeteria.productos.dto.request;

import com.cafeteria.productos.enums.CategoryEnum;

public class ProductsRequestDTO {

    private String product_name;
    private CategoryEnum category;
    private float price;
    private boolean product_active;

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
