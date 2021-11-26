package com.jwd.fShop.dao.exception;

public class FatalDaoException extends  Exception {
    public FatalDaoException(final String message){
        super(message);
    }
    public FatalDaoException(final String message, final Exception cause){
        super(message, cause);
    }
    public FatalDaoException(final Exception cause){
        super(cause);
    }
}
