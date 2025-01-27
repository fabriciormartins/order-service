package br.com.mouts.order.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "br.com.mouts.order.application")
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericError> handlerIllegalArgumaentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new GenericError(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
