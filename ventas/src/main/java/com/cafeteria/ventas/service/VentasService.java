package com.cafeteria.ventas.service;

import com.cafeteria.ventas.dto.request.DetalleVentaRequestDTO;
import com.cafeteria.ventas.dto.request.VentasRequestDTO;
import com.cafeteria.ventas.dto.response.DetalleVentaResponseDTO;
import com.cafeteria.ventas.dto.response.VentasResponseDTO;
import com.cafeteria.ventas.entity.Venta;
import com.cafeteria.ventas.enums.APIError;
import com.cafeteria.ventas.exceptions.VentasExceptions;
import com.cafeteria.ventas.repository.VentasRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class VentasService {

    private final VentasRepository ventasRepository;
    private final ConversionService conversionService;

    public VentasService(VentasRepository ventasRepository, ConversionService conversionService) {
        this.ventasRepository = ventasRepository;
        this.conversionService = conversionService;
    }

    @Transactional(readOnly = true)
    public List<VentasResponseDTO> getAllVentas() {
        return conversionService.convert(ventasRepository.findAll(), List.class);
    }

    @Transactional(readOnly = true)
    public VentasResponseDTO getVentasById(Long id) {
        Venta venta = ventasRepository.findById(id).orElseThrow(() -> new VentasExceptions(APIError.RESOURCE_NOT_FOUND));

        return conversionService.convert(venta, VentasResponseDTO.class);
    }

//    private List<VentasResponseDTO> getVentasResponse(Long ventaId) {
//        String filtroVenta = ventaId == null ? "" : " where venta_id = :ventaId";
//        Query ventasQuery = entityManager.createNativeQuery(
//                "select venta_id, coalesce(total, 0) from ventas"
//                        + filtroVenta + " order by venta_id"
//        );
//        if (ventaId != null) {
//            ventasQuery.setParameter("ventaId", ventaId);
//        }
//        List<?> ventaRows = ventasQuery.getResultList();
//        Map<Long, VentasResponseDTO> ventas = new LinkedHashMap<>();
//
//        for (Object result : ventaRows) {
//            Object[] row = (Object[]) result;
//            VentasResponseDTO venta = new VentasResponseDTO();
//            venta.setVentaId(((Number) row[0]).longValue());
//            venta.setTotal(((Number) row[1]).floatValue());
//            venta.setDetalles(new ArrayList<>());
//            ventas.put(venta.getVentaId(), venta);
//        }
//
//        String filtroDetalle = ventaId == null ? "" : " where venta_id = :ventaId";
//        Query detallesQuery = entityManager.createNativeQuery(
//                "select venta_id, detalle_id, producto_id, cantidad_producto, "
//                        + "coalesce(precio_unitario, 0), "
//                        + "coalesce(subtotal, precio_unitario * cantidad_producto, 0) "
//                        + "from detalle_venta" + filtroDetalle + " order by venta_id, detalle_id"
//        );
//        if (ventaId != null) {
//            detallesQuery.setParameter("ventaId", ventaId);
//        }
//        List<?> detalleRows = detallesQuery.getResultList();
//
//        for (Object result : detalleRows) {
//            Object[] row = (Object[]) result;
//            VentasResponseDTO venta = ventas.get(((Number) row[0]).longValue());
//            if (venta == null) {
//                continue;
//            }
//
//            DetalleVentaResponseDTO detalle = new DetalleVentaResponseDTO();
//            detalle.setDetalleId(((Number) row[1]).longValue());
//            detalle.setProductoId(((Number) row[2]).longValue());
//            detalle.setCantidadProducto(row[3] == null ? null : ((Number) row[3]).intValue());
//            detalle.setPrecioUnitario(((Number) row[4]).floatValue());
//            detalle.setSubtotal(((Number) row[5]).floatValue());
//            venta.getDetalles().add(detalle);
//        }
//
//        return new ArrayList<>(ventas.values());
//    }

//    @Transactional
//    public VentasResponseDTO create(VentasRequestDTO requestDTO) {
//        validateRequest(requestDTO);
//
//        Venta venta = conversionService.convert(requestDTO, Venta.class);
//        if (venta == null) {
//            throw new VentasExceptions(APIError.BAD_FORMAT);
//        }
//
//        calcularSubtotalesYTotal(venta);
//
//        Venta saved = ventasRepository.save(venta);
//        return conversionService.convert(saved, VentasResponseDTO.class);
//    }
//
//    @Transactional
//    public VentasResponseDTO update(Long id, VentasRequestDTO requestDTO) {
//        if (id == null || id <= 0) {
//            throw new VentasExceptions(APIError.VALIDATION_ERROR);
//        }
//
//        Venta existing = ventasRepository.findById(id)
//                .orElseThrow(() -> new VentasExceptions(APIError.RESOURCE_NOT_FOUND));
//
//        validateRequest(requestDTO);
//
//        Venta updatedData = conversionService.convert(requestDTO, Venta.class);
//        if (updatedData == null) {
//            throw new VentasExceptions(APIError.BAD_FORMAT);
//        }
//
//        if (existing.getDetalles() == null) {
//            existing.setDetalles(new ArrayList<>());
//        } else {
//            existing.getDetalles().clear();
//        }
//        existing.getDetalles().addAll(updatedData.getDetalles());
//        calcularSubtotalesYTotal(existing);
//
//        Venta saved = ventasRepository.save(existing);
//        return conversionService.convert(saved, VentasResponseDTO.class);
//    }
//
//    @Transactional
//    public void delete(Long id) {
//        if (id == null || id <= 0) {
//            throw new VentasExceptions(APIError.VALIDATION_ERROR);
//        }
//        if (!ventasRepository.existsById(id)) {
//            throw new VentasExceptions(APIError.RESOURCE_NOT_FOUND);
//        }
//        ventasRepository.deleteById(id);
//    }
//
//    private void validateRequest(VentasRequestDTO requestDTO) {
//        if (requestDTO == null || requestDTO.getDetalles() == null || requestDTO.getDetalles().isEmpty()) {
//            throw new VentasExceptions(APIError.VALIDATION_ERROR);
//        }
//        for (DetalleVentaRequestDTO detalle : requestDTO.getDetalles()) {
//            if (detalle.getProductoId() == null || detalle.getProductoId() <= 0) {
//                throw new VentasExceptions(APIError.VALIDATION_ERROR);
//            }
//            if (detalle.getCantidadProducto() == null || detalle.getCantidadProducto() <= 0) {
//                throw new VentasExceptions(APIError.VALIDATION_ERROR);
//            }
//            if (detalle.getPrecioUnitario() <= 0) {
//                throw new VentasExceptions(APIError.VALIDATION_ERROR);
//            }
//        }
//    }

    // Metodo para calcular los subtotales y el total de la venta V1
//    private void calcularSubtotalesYTotal(Venta venta) {
//        if (venta.getDetalles() != null) {
//            for (DetalleVenta detalle : venta.getDetalles()) {
//                detalle.setVenta(venta);
//                if (detalle.getPrecioUnitario() != null && detalle.getCantidadProducto() != null) {
//                    detalle.setSubtotal(
//                            detalle.getPrecioUnitario()
//                                    .multiply(BigDecimal.valueOf(detalle.getCantidadProducto()))
//                    );
//                }
//            }
//            BigDecimal total = venta.getDetalles().stream()
//                    .map(d -> d.getSubtotal() != null ? d.getSubtotal() : BigDecimal.ZERO)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//            venta.setTotal(total);
//        }
//    }

    // Metodo para calcular los subtotales y el total de la venta V2
//    private void calcularSubtotalesYTotal(Venta venta) {
//        float subtotalTotal = (float) venta.getDetalles().stream()
//                .mapToDouble(detalle -> {
//                    detalle.setVenta(venta);
//                    float subtotal = detalle.getPrecioUnitario() * detalle.getCantidadProducto();
//                    detalle.setSubtotal(subtotal);
//                    return subtotal;
//                })
//                .sum();
//        venta.setTotal(subtotalTotal);
//    }
}
