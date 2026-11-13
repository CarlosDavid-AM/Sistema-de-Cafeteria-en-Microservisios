package com.cafeteria.ventas.repository;

import com.cafeteria.ventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentasRepository extends JpaRepository<Venta, Long> {
}
