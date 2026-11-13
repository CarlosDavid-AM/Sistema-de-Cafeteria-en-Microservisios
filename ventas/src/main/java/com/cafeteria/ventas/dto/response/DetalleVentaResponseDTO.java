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

    public Long getDetalleId() { return detalleId; }
    public void setDetalleId(Long detalleId) { this.detalleId = detalleId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public Integer getCantidadProducto() { return cantidadProducto; }
    public void setCantidadProducto(Integer cantidadProducto) { this.cantidadProducto = cantidadProducto; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
