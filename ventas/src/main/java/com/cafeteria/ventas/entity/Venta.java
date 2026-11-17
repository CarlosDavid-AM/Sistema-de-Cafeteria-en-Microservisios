package com.cafeteria.ventas.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Long ventaId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "venta_id")
    private List<DetalleVenta> detalles;

    private float total;

    public Long getVentaId() { return ventaId; }
    public void setVentaId(Long ventaId) { this.ventaId = ventaId; }

    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return Float.compare(total, venta.total) == 0 && Objects.equals(ventaId, venta.ventaId) && Objects.equals(detalles, venta.detalles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ventaId, detalles, total);
    }

    @Override
    public String toString() {
        return "Venta{" +
                "ventaId=" + ventaId +
                ", detalles=" + detalles +
                ", total=" + total +
                '}';
    }
}
