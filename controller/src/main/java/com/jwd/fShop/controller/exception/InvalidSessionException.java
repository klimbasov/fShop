package com.jwd.fShop.controller.exception;

public class InvalidSessionException extends Exception {
    public InvalidSessionException() {
        super();
    }

    public InvalidSessionException(final String message) {
        super(message);
    }

    public InvalidSessionException(final String message, final Exception cause) {
        super(message, cause);
    }

    public InvalidSessionException(final Exception cause) {
        super(cause);
    }
}
