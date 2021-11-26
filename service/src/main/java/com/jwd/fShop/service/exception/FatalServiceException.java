package com.jwd.fShop.service.exception;

public class FatalServiceException extends Exception {
    public FatalServiceException(final String message){
        super(message);
    }
    public FatalServiceException(final String message, final Exception cause){
        super(message, cause);
    }
    public FatalServiceException(final Exception cause){
        super(cause);
    }
}
