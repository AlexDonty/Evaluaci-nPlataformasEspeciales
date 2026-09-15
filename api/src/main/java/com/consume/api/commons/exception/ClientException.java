package com.consume.api.commons.exception;

public class ClientException extends RuntimeException {

    private final int status;

    public ClientException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}