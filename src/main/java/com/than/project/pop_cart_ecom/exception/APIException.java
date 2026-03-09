package com.than.project.pop_cart_ecom.exception;

public class APIException extends RuntimeException{

    private static final long serialVersionUUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
