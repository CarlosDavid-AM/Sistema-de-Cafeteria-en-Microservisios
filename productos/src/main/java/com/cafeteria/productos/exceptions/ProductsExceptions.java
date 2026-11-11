package com.cafeteria.productos.exceptions;

import com.cafeteria.productos.enums.APIError;
import org.springframework.http.HttpStatus;

public class ProductsExceptions extends RuntimeException {

    private HttpStatus status;
    private String message;

    public ProductsExceptions(APIError apiError) {
        this.status = apiError.getHttpStatus();
        this.message = apiError.getMessage();
    }

    public ProductsExceptions(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
