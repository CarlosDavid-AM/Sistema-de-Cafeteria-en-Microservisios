package com.cafeteria.inventario.conector;

import com.cafeteria.inventario.dto.response.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductConnector {

    private final RestClient restClient;

    public ProductConnector(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductDTO getProductById(Long productId) {

        ProductDTO response = restClient.get()
                .uri("http://localhost:8082/api/v1/products/{productId}", productId)
                .retrieve()
                .body(ProductDTO.class);

        return response.getClass().cast(response);
    }
}
