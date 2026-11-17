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

        float totalFinal = calcularTotalAndSubTotal(transformed);
        transformed.setTotal(totalFinal);

        Venta saved = ventasRepository.save(Objects.requireNonNull(transformed));

        return  conversionService.convert(saved, VentasResponseDTO.class);
    }

    @Transactional
    public VentasResponseDTO updateVenta(Long id, VentasRequestDTO requestDTO) {
        if (getVentasById(id) == null) {
            throw new  VentasExceptions(APIError.RESOURCE_NOT_FOUND);
        }

        Venta transformed = conversionService.convert(requestDTO, Venta.class);
        Objects.requireNonNull(transformed).setVentaId(id); // Establecer el ID de la venta existente para la actualización

        float totalFinal = calcularTotalAndSubTotal(transformed);
        transformed.setTotal(totalFinal);

        Venta saved = ventasRepository.save(Objects.requireNonNull(transformed));

        return conversionService.convert(saved, VentasResponseDTO.class);
    }

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

    private float calcularTotalAndSubTotal (Venta venta) {
        float subTotalFinal = 0;
        float totalFinal = 0;
        for (DetalleVenta detalle : Objects.requireNonNull(venta).getDetalles()) {
            subTotalFinal += detalle.getPrecioUnitario() * detalle.getCantidadProducto();
            detalle.setSubtotal(subTotalFinal);
            subTotalFinal = 0; // Reiniciar el subtotal para el siguiente detalle

            // Calcular el total de la venta sumando los subtotales de los detalles
            totalFinal += detalle.getSubtotal();
        }

        return totalFinal;
    }
}
