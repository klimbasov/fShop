package com.jwd.fShop.service.exception;

public class ServiceException extends Exception{
    public ServiceException(final String message){
        super(message);
    }
    public ServiceException(final String message, final Exception cause){
        super(message, cause);
    }
    public ServiceException(final Exception cause){
        super(cause);
    }
}
