package com.cafeteria.productos.exceptions;

import com.cafeteria.productos.dto.response.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(ProductsExceptions.class)
    public ResponseEntity<ErrorDTO> handleProductsException(ProductsExceptions e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage());

        return ResponseEntity.status(e.getHttpStatus()).body(errorDTO);
    }
}
