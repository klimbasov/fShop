package com.jwd.fShop.dao.exception;

public class DaoException extends Exception {
    public DaoException(final String message){
        super(message);
    }
    public DaoException(final String message, final Exception cause){
        super(message, cause);
    }
    public DaoException(final Exception cause){
        super(cause);
    }
}
