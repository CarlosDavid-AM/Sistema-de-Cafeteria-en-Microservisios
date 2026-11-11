package com.cafeteria.inventario.repository;

import com.cafeteria.inventario.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
