package com.jwd.fShop.controller.command.impl;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutUserCommand extends AbstractCommand implements Command {
    public LogoutUserCommand() {
        super(Role.REGISTERED_USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = req.getSession();
        session.removeAttribute("userName");
        session.setAttribute("role", Role.UNREGISTERED_USER.name());

        try {
            resp.sendRedirect("FrontController");
        } catch (Exception e) {
            throw new CommandException("In " + this.getClass().getName() + " while forwarding.", e);
        }
    }
}
