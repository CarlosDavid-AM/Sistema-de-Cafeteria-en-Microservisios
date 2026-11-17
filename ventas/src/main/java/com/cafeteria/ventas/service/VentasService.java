package com.cafeteria.ventas.service;

import com.cafeteria.ventas.dto.request.VentasRequestDTO;
import com.cafeteria.ventas.dto.response.VentasResponseDTO;
import com.cafeteria.ventas.entity.DetalleVenta;
import com.cafeteria.ventas.entity.Venta;
import com.cafeteria.ventas.enums.APIError;
import com.cafeteria.ventas.exceptions.VentasExceptions;
import com.cafeteria.ventas.repository.VentasRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public VentasResponseDTO createVentas(VentasRequestDTO ventasRequestDTO) {
        Venta transformed = conversionService.convert(ventasRequestDTO, Venta.class);

        // Calcular el SUBtotal de la venta sumando los precios de los detalles
        float subTotalFinal = 0;
        float totalFinal = 0;
        for (DetalleVenta detalle : Objects.requireNonNull(transformed).getDetalles()) {
            subTotalFinal += detalle.getPrecioUnitario() * detalle.getCantidadProducto();
            detalle.setSubtotal(subTotalFinal);
            subTotalFinal = 0; // Reiniciar el subtotal para el siguiente detalle

            // Calcular el total de la venta sumando los subtotales de los detalles
            totalFinal += detalle.getSubtotal();
        }

        transformed.setTotal(totalFinal);

        Venta saved = ventasRepository.save(Objects.requireNonNull(transformed));

        return  conversionService.convert(saved, VentasResponseDTO.class);
    }

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

    @Transactional
    public void deleteVenta(Long id) {
        if (getVentasById(id) == null) {
            throw new  VentasExceptions(APIError.RESOURCE_NOT_FOUND);
        }

        ventasRepository.deleteById(id);
    }

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
