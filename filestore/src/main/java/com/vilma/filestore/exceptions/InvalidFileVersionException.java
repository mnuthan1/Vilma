package com.vilma.filestore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidFileVersionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidFileVersionException(String message) {
        super(message);
    }

    public InvalidFileVersionException(String message, Throwable cause) {
        super(message, cause);
    }
}