package com.cafeteria.ventas.dto.request;

import java.math.BigDecimal;

public class DetalleVentaRequestDTO {

    private Long productoId;

    private Integer cantidadProducto;

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidadProducto() { return cantidadProducto; }
    public void setCantidadProducto(Integer cantidadProducto) { this.cantidadProducto = cantidadProducto; }
}
