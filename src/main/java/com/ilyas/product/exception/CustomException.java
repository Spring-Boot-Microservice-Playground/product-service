package com.ilyas.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Order")
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
    public CustomException(String message, Throwable cause){
        super(message, cause);
    }
}
