package com.cafeteria.ventas.dto.request;

import java.util.ArrayList;
import java.util.List;

public class VentasRequestDTO {

    private List<DetalleVentaRequestDTO> detalles = new ArrayList<>();

    public List<DetalleVentaRequestDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVentaRequestDTO> detalles) { this.detalles = detalles; }
}
