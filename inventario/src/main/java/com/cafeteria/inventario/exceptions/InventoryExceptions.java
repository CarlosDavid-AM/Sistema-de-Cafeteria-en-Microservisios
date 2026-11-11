package com.cafeteria.inventario.exceptions;

import com.cafeteria.inventario.enums.APIError;
import org.springframework.http.HttpStatus;

public class InventoryExceptions extends RuntimeException {

    private HttpStatus status;
    private String message;

    public InventoryExceptions(APIError apiError) {
        this.status = apiError.getHttpStatus();
        this.message = apiError.getMessage();
    }

    public InventoryExceptions(HttpStatus status, String message) {
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
