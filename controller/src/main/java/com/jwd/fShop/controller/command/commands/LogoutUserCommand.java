package com.jwd.fShop.controller.command.commands;

import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutUserCommand extends AbstractCommand implements Command{
    public LogoutUserCommand(ServicePack servicePack) {
        super(servicePack, Role.REGISTERED_USER);
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
