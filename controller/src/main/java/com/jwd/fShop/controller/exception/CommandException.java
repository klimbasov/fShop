package com.jwd.fShop.controller.exception;

public class CommandException extends Exception {

    public CommandException(final String message) {
        super(message);
    }

    public CommandException(final String message, final Exception cause) {
        super(message, cause);
    }

    public CommandException(final Exception cause) {
        super(cause);
    }
}
