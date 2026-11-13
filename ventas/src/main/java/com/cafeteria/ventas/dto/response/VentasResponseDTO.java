package com.cafeteria.ventas.dto.response;

import com.cafeteria.ventas.entity.DetalleVenta;

import java.math.BigDecimal;
import java.util.List;

public class VentasResponseDTO {

    private Long ventaId;

    private List<DetalleVentaResponseDTO> detalles;

    private BigDecimal total;

}
