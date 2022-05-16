package com.ilyas.product.exception;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> generealError(HttpRequest req, Exception exception){
        CustomExceptionPayload payload = new CustomExceptionPayload(exception.getMessage(), req.uri().getRawPath(), LocalDateTime.now());
        return ResponseEntity.internalServerError().body(payload);
    }
    
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<?> error(HttpRequest req, CustomException exception){
        CustomExceptionPayload payload = new CustomExceptionPayload(exception.getMessage(), req.uri().getRawPath(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(payload);
    }
}
