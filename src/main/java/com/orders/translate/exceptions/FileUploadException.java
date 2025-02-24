package com.orders.translate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class FileUploadException extends RuntimeException {

    public FileUploadException(String ex) {
            super(ex);
    }

	public FileUploadException(String ex, Throwable cause) {
            super(ex, cause);
        }
}

