package com.cafeteria.ventas.dto.response;

import com.cafeteria.ventas.entity.Venta;

import java.math.BigDecimal;

public class DetalleVentaResponseDTO {

    private Long detalleId;

    private Long productoId;

    private String nombreProducto;

    private Integer cantidadProducto;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

}
