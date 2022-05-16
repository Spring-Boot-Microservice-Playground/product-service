package com.ilyas.product.exception;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CustomExceptionPayload{
    final String message;
    final String uri;
    final LocalDateTime localDateTime;
}