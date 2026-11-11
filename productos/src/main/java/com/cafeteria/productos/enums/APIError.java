package com.cafeteria.productos.enums;

import org.springframework.http.HttpStatus;

public enum APIError {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "There are attributes with wrong values"),
    BAD_FORMAT(HttpStatus.BAD_REQUEST, "The message does not have a correct format"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested resource was not found"),
    RESOURCE_CONFLICT(HttpStatus.CONFLICT, "The resource conflicts with the current state"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");

    private final HttpStatus httpStatus;
    private final String message;

    APIError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
