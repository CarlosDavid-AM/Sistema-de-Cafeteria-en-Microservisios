package com.cafeteria.inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name="inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="inventory_id", nullable=false)
    private Long inventoryId;

    @NotNull
    @Column(name="product_id", nullable=false)
    private Long productId;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    @Column(name="minimum_stock", nullable=false)
    private Integer minimumStock;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }
}
