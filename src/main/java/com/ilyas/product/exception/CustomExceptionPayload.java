package com.ilyas.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CustomExceptionPayload{
    final String message;
    final HttpStatus httpStatus;
    final LocalDateTime localDateTime;
}