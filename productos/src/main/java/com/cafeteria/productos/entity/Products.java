package com.cafeteria.productos.entity;

import com.cafeteria.productos.enums.CategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name="products")
public class Products {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="product_id", nullable=false)
    private Long product_id;

    @NotEmpty(message = "Product name is required")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    @Column(name="product_name", nullable=false)
    private String product_name;

    @Enumerated(EnumType.STRING)
    @Column(name="category", nullable=false)
    private CategoryEnum category;

    @NotNull(message = "Price is required")
    @Column(name="price", nullable=false)
    @Positive(message = "Price must be a positive value")
    private float price;

    private boolean product_active = true;

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
