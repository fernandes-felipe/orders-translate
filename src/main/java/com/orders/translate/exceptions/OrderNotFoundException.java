package com.orders.translate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String ex) {
        super(ex);
    }

    public OrderNotFoundException(String ex, Throwable cause) {
        super(ex, cause);
    }
}
