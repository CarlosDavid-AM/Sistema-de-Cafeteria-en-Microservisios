package com.cafeteria.inventario.exceptions;

import com.cafeteria.inventario.dto.response.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(InventoryExceptions.class)
    public ResponseEntity<ErrorDTO> handleProductsException(InventoryExceptions e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage());

        return ResponseEntity.status(e.getHttpStatus()).body(errorDTO);
    }
}
