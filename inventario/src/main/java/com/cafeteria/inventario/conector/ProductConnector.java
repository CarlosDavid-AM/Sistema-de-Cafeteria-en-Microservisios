package com.cafeteria.inventario.conector;

import com.cafeteria.inventario.dto.response.ProductDTO;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductConnector {

    private static final String SERVICE_NAME = "SERVICIO-PRODUCTOS";
    private static final String PRODUCTS_PATH = "/api/v1/products/{productId}";

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public ProductConnector(RestClient restClient, DiscoveryClient discoveryClient) {
        this.restClient = restClient;
        this.discoveryClient = discoveryClient;
    }

    public ProductDTO getProductById(Long productId) {

        ServiceInstance instance = discoveryClient.getInstances(SERVICE_NAME)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró ninguna instancia registrada para: " + SERVICE_NAME));

        String baseUrl = instance.getUri().toString();

        ProductDTO response = restClient.get()
                .uri(baseUrl + PRODUCTS_PATH, productId)
                .retrieve()
                .body(ProductDTO.class);

        return response.getClass().cast(response);
    }
}
