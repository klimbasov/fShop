package com.jwd.fShop.controller.command.impl;


import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidSessionException;
import com.jwd.fShop.controller.validator.Validator.Validator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jwd.fShop.controller.constant.Parameters.*;
import static com.jwd.fShop.controller.constant.Attributes.*;

public abstract class AbstractCommand {
    protected Validator validator;
    protected final Role role;

    protected AbstractCommand(final Role role) {
        this.role = role;
    }

    protected void validateSessionRole(final Integer role) throws InvalidSessionException {
        if (!this.role.hasAccess(role)) {
            throw new InvalidSessionException();
        }
    }

    protected void validateSessionRole(final String role) throws InvalidSessionException {
        if (this.role.hasAccess(role)) {
            return;
        } else {
            throw new InvalidSessionException();
        }
    }

    protected void validateRole(HttpServletRequest req, HttpServletResponse resp, int status, String message) throws CommandException {
        try {
            validateSessionRole((String) (req.getSession().getAttribute("role")));
        }catch (InvalidSessionException exception) {
            resp.setStatus(status);
            throw new CommandException(message, exception);
        }
    }
}
