package com.cafeteria.ventas.dto.response;

import com.cafeteria.ventas.entity.DetalleVenta;

import java.math.BigDecimal;
import java.util.List;

public class VentasResponseDTO {

    private Long ventaId;

    private List<DetalleVentaResponseDTO> detalles;

    private float total;

    public Long getVentaId() { return ventaId; }
    public void setVentaId(Long ventaId) { this.ventaId = ventaId; }

    public List<DetalleVentaResponseDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVentaResponseDTO> detalles) { this.detalles = detalles; }

    public Float getTotal() { return total; }
    public void setTotal(Float total) { this.total = total; }
}
