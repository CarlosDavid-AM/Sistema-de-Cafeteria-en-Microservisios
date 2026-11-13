package com.cafeteria.ventas.exceptions;

import com.cafeteria.ventas.enums.APIError;
import org.springframework.http.HttpStatus;

public class VentasExceptions extends RuntimeException {

    private HttpStatus status;
    private String message;

    public VentasExceptions(APIError apiError) {
        this.status = apiError.getHttpStatus();
        this.message = apiError.getMessage();
    }

    public VentasExceptions(HttpStatus status, String message) {
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
