package com.cafeteria.ventas.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Long detalleId;

    @Column(name = "producto_id")
    private Long productoId;

    @Column(name = "cantidad_producto")
    private Integer cantidadProducto;

    @Column(name = "precio_unitario")
    private float precioUnitario;

    private float subtotal;

    public Long getDetalleId() { return detalleId; }
    public void setDetalleId(Long detalleId) { this.detalleId = detalleId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidadProducto() { return cantidadProducto; }
    public void setCantidadProducto(Integer cantidadProducto) { this.cantidadProducto = cantidadProducto; }

    public float getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(float precioUnitario) { this.precioUnitario = precioUnitario; }

    public float getSubtotal() { return subtotal; }
    public void setSubtotal(float subtotal) { this.subtotal = subtotal; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetalleVenta that = (DetalleVenta) o;
        return Float.compare(precioUnitario, that.precioUnitario) == 0 && Float.compare(subtotal, that.subtotal) == 0 && Objects.equals(detalleId, that.detalleId) && Objects.equals(productoId, that.productoId) && Objects.equals(cantidadProducto, that.cantidadProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detalleId, productoId, cantidadProducto, precioUnitario, subtotal);
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "detalleId=" + detalleId +
                ", productoId=" + productoId +
                ", cantidadProducto=" + cantidadProducto +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}