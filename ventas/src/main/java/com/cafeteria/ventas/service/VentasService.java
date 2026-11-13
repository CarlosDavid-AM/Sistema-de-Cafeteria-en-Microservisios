package com.cafeteria.ventas.service;

import com.cafeteria.ventas.dto.request.DetalleVentaRequestDTO;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentasService {

    private final VentasRepository ventasRepository;
    private final ConversionService conversionService;

    public VentasService(VentasRepository ventasRepository, ConversionService conversionService) {
        this.ventasRepository = ventasRepository;
        this.conversionService = conversionService;
    }

    @Transactional(readOnly = true)
    public List<VentasResponseDTO> getAll() {
        List<Venta> ventas = ventasRepository.findAll();
        return ventas.stream()
                .map(venta -> conversionService.convert(venta, VentasResponseDTO.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public VentasResponseDTO getById(Long id) {
        if (id == null || id <= 0) {
            throw new VentasExceptions(APIError.VALIDATION_ERROR);
        }
        Venta venta = ventasRepository.findById(id)
                .orElseThrow(() -> new VentasExceptions(APIError.RESOURCE_NOT_FOUND));
        return conversionService.convert(venta, VentasResponseDTO.class);
    }

    @Transactional
    public VentasResponseDTO create(VentasRequestDTO requestDTO) {
        validateRequest(requestDTO);

        Venta venta = conversionService.convert(requestDTO, Venta.class);
        if (venta == null) {
            throw new VentasExceptions(APIError.BAD_FORMAT);
        }

        calcularSubtotalesYTotal(venta);

        Venta saved = ventasRepository.save(venta);
        return conversionService.convert(saved, VentasResponseDTO.class);
    }

    @Transactional
    public VentasResponseDTO update(Long id, VentasRequestDTO requestDTO) {
        if (id == null || id <= 0) {
            throw new VentasExceptions(APIError.VALIDATION_ERROR);
        }

        Venta existing = ventasRepository.findById(id)
                .orElseThrow(() -> new VentasExceptions(APIError.RESOURCE_NOT_FOUND));

        validateRequest(requestDTO);

        Venta updatedData = conversionService.convert(requestDTO, Venta.class);
        if (updatedData == null) {
            throw new VentasExceptions(APIError.BAD_FORMAT);
        }

        if (existing.getDetalles() == null) {
            existing.setDetalles(new ArrayList<>());
        } else {
            existing.getDetalles().clear();
        }

        if (updatedData.getDetalles() != null) {
            for (DetalleVenta detalle : updatedData.getDetalles()) {
                detalle.setVenta(existing);
                if (detalle.getPrecioUnitario() != null && detalle.getCantidadProducto() != null) {
                    detalle.setSubtotal(
                            detalle.getPrecioUnitario()
                                    .multiply(BigDecimal.valueOf(detalle.getCantidadProducto()))
                    );
                }
                existing.getDetalles().add(detalle);
            }
        }

        BigDecimal total = existing.getDetalles().stream()
                .map(d -> d.getSubtotal() != null ? d.getSubtotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        existing.setTotal(total);

        Venta saved = ventasRepository.save(existing);
        return conversionService.convert(saved, VentasResponseDTO.class);
    }

    @Transactional
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new VentasExceptions(APIError.VALIDATION_ERROR);
        }
        if (!ventasRepository.existsById(id)) {
            throw new VentasExceptions(APIError.RESOURCE_NOT_FOUND);
        }
        ventasRepository.deleteById(id);
    }

    private void validateRequest(VentasRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getDetalles() == null || requestDTO.getDetalles().isEmpty()) {
            throw new VentasExceptions(APIError.VALIDATION_ERROR);
        }
        for (DetalleVentaRequestDTO detalle : requestDTO.getDetalles()) {
            if (detalle.getProductoId() == null || detalle.getProductoId() <= 0) {
                throw new VentasExceptions(APIError.VALIDATION_ERROR);
            }
            if (detalle.getCantidadProducto() == null || detalle.getCantidadProducto() <= 0) {
                throw new VentasExceptions(APIError.VALIDATION_ERROR);
            }
            if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                throw new VentasExceptions(APIError.VALIDATION_ERROR);
            }
        }
    }

    private void calcularSubtotalesYTotal(Venta venta) {
        if (venta.getDetalles() != null) {
            for (DetalleVenta detalle : venta.getDetalles()) {
                detalle.setVenta(venta);
                if (detalle.getPrecioUnitario() != null && detalle.getCantidadProducto() != null) {
                    detalle.setSubtotal(
                            detalle.getPrecioUnitario()
                                    .multiply(BigDecimal.valueOf(detalle.getCantidadProducto()))
                    );
                }
            }
            BigDecimal total = venta.getDetalles().stream()
                    .map(d -> d.getSubtotal() != null ? d.getSubtotal() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            venta.setTotal(total);
        }
    }
}
