package com.cafeteria.ventas.exceptions;

import com.cafeteria.ventas.dto.response.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(VentasExceptions.class)
    public ResponseEntity<ErrorDTO> handleProductsException(VentasExceptions e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage());

        return ResponseEntity.status(e.getHttpStatus()).body(errorDTO);
    }
}
