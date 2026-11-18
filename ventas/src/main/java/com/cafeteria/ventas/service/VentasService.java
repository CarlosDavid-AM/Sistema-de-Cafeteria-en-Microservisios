package com.cafeteria.ventas.service;

import com.cafeteria.ventas.conector.ProductConnector;
import com.cafeteria.ventas.dto.request.VentasRequestDTO;
import com.cafeteria.ventas.dto.response.DetalleVentaResponseDTO;
import com.cafeteria.ventas.dto.response.ProductDTO;
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
    private final ProductConnector productConnector;

    public VentasService(VentasRepository ventasRepository, ConversionService conversionService, ProductConnector productConnector) {
        this.ventasRepository = ventasRepository;
        this.conversionService = conversionService;
        this.productConnector = productConnector;
    }

    // * Metodo de prueba *
    // Metodo para obtener los detalles de un producto
    public ProductDTO getProductById(Long productId) {
        return productConnector.getProductById(productId);
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

        ProductDTO productDTO = getProductById(Objects.requireNonNull(transformed).getDetalles().getFirst().getProductoId());
        for (DetalleVenta detalle : Objects.requireNonNull(transformed).getDetalles()) {
            detalle.setPrecioUnitario(productDTO.getPrice());
        }


        float totalFinal = calcularTotalAndSubTotal(transformed);
        transformed.setTotal(totalFinal);

        Venta saved = ventasRepository.save(Objects.requireNonNull(transformed));

        VentasResponseDTO result =  conversionService.convert(saved, VentasResponseDTO.class);

        return enriquecerDetallesConNombreProducto(result);
    }


    /**
     * Solucionar el problema de acctualizar los detalles de la venta, ya que al actualizar una venta,
     * los detalles no se actualizan correctamente.
     */
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

        VentasResponseDTO result = conversionService.convert(saved, VentasResponseDTO.class);

        return enriquecerDetallesConNombreProducto(result);
    }

    @Transactional
    public void deleteVenta(Long id) {
        if (getVentasById(id) == null) {
            throw new  VentasExceptions(APIError.RESOURCE_NOT_FOUND);
        }

        ventasRepository.deleteById(id);
    }

    private VentasResponseDTO enriquecerDetallesConNombreProducto(VentasResponseDTO ventasResponseDTO) {
        for (DetalleVentaResponseDTO detalleResponse : Objects.requireNonNull(ventasResponseDTO).getDetalles()) {
            ProductDTO product = productConnector.getProductById(detalleResponse.getProductoId());
            detalleResponse.setNombreProducto(product.getProduct_name());
        }
        return ventasResponseDTO;
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
