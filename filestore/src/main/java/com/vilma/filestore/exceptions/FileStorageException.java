package com.vilma.filestore.exceptions;

public class FileStorageException extends RuntimeException {
    private static final long serialVersionUID = -2719446821897394465L;

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}